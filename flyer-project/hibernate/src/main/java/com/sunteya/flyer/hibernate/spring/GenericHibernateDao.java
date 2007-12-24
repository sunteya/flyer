/**
 * Created on 2006-9-5
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate.spring;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.SerializationUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunteya.commons.util.GenericUtils;
import com.sunteya.flyer.dao.GenericDao;
import com.sunteya.flyer.domain.Entity;
import com.sunteya.flyer.support.Pagination;
import com.sunteya.flyer.support.PaginationStrategy;
import com.sunteya.flyer.support.RequestPage;

/**
 *
 * @author Sunteya
 * @email Sunteya@gmail.com
 */
public abstract class GenericHibernateDao<E extends Entity> extends HibernateDaoSupport
		implements GenericDao<E> {

	private Class<? extends Entity> entityClass;

	public GenericHibernateDao() {
		entityClass = GenericUtils.getSuperClassTypeArgument(this, 0);
	}

	public GenericHibernateDao(Class<? extends Entity> entityClass) {
		this.entityClass = entityClass;
	}

	public Class<? extends Entity> getEntityClass() {
		return entityClass;
	}
	
	// =====================================================
	// 工具方法
	// -----------------------------------------------------
	protected DetachedCriteria createEntityDetachedCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
		return criteria;
	}

	@SuppressWarnings("unchecked")
	protected <Obj> Obj queryUniqueResultByCriteria(final DetachedCriteria criteria) {
		return (Obj) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return criteria.getExecutableCriteria(session).uniqueResult();
			}
		}, true);
	}

	protected E findFirstEntityByCriteria(DetachedCriteria criteria) {
		List<E> rs = findEntitiesByCriteria(criteria, 0, 1);
		return rs.isEmpty() ? null : rs.get(0);
	}


	@SuppressWarnings("unchecked")
	protected List<E> findEntitiesByCriteria(DetachedCriteria criteria) {
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	protected List<E> findEntitiesByCriteria(DetachedCriteria criteria,
			final int firstResult, final int maxResults) {
		return getHibernateTemplate().findByCriteria(criteria, firstResult,
				maxResults);
	}

	protected Pagination<E> findPageByCriteria(DetachedCriteria criteria, RequestPage requestPage) {
		return findPageByCriteria(criteria, requestPage, null);
	}

	protected Pagination<E> findPageByCriteria(DetachedCriteria criteria, RequestPage requestPage, Order order) {
		return findPageByCriteria(criteria, requestPage.getRequestPageIndex(), requestPage.getRequestPageSize(), order);
	}

	protected Pagination<E> findPageByCriteria(DetachedCriteria criteria,
			int pageIndex, int pageSize) {
		return findPageByCriteria(criteria, pageIndex, pageSize, null);
	}

	@SuppressWarnings("unchecked")
	protected Pagination<E> findPageByCriteria(DetachedCriteria criteria, int pageIndex, int pageSize, Order order) {
		DetachedCriteria clone = (DetachedCriteria) SerializationUtils.clone(criteria);
		clone.setProjection(Projections.rowCount());
		int count = (Integer) queryUniqueResultByCriteria(clone);

		PaginationStrategy strategy = new PaginationStrategy(pageIndex,	pageSize, count);

		if (order != null) {
			criteria.addOrder(order);
		}

		List<E> elements = getHibernateTemplate()
				.findByCriteria(criteria,
						strategy.getAllPreviousPageItemsCount(),
						strategy.getPageSize());

		return new Pagination<E>(strategy, elements);
	}

	@SuppressWarnings("unchecked")
	public E createEntity() {
		try {
			return (E) getEntityClass().newInstance();
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public void flushAndRefresh(E entity) {
		getHibernateTemplate().flush();
		getHibernateTemplate().refresh(entity);
	}

	// =====================================================
	// Dao方法
	// -----------------------------------------------------
	@SuppressWarnings("unchecked")
	public E get(Serializable id) {
		return (E) getHibernateTemplate().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public E findById(Serializable id) {
		if(id == null) {
			return null;
		}

		return (E) getHibernateTemplate().get(entityClass, id);
	}

	public List<E> findAllByMap(Map<String, Object> param, boolean ignoreCase) {
		DetachedCriteria criteria = createEntityDetachedCriteria();

		for (Map.Entry<String, Object> entry : param.entrySet()) {
			String propertyName = entry.getKey();
			Object value = entry.getValue();
			if(value == null) {
				criteria.add(Restrictions.isNotNull(propertyName));
			} else if (value instanceof String && ignoreCase) {
				criteria.add(Restrictions.eq(propertyName, value).ignoreCase());
			} else {
				criteria.add(Restrictions.eq(propertyName, value));
			}
		}

		return findEntitiesByCriteria(criteria);
	}

	public void remove(E t) {
		getHibernateTemplate().delete(t);
	}

	public void save(E t) {
		getHibernateTemplate().save(t);
	}

	public void update(E t) {
		getHibernateTemplate().update(t);
	}

	public void saveOrUpdate(E t) {
		getHibernateTemplate().saveOrUpdate(t);
	}

	public E create() {
		return createEntity();
	}

	public void removeById(Serializable id) {
		remove(get(id));
	}

	public List<E> findAll() {
		return findEntitiesByCriteria(createEntityDetachedCriteria());
	}

	public List<E> findAllByIds(Collection<? extends Serializable> ids) {
		if(CollectionUtils.isEmpty(ids)) {
			return new ArrayList<E>();
		}

		DetachedCriteria criteria = createEntityDetachedCriteria()
			.add(Restrictions.in("id", ids));
		return findEntitiesByCriteria(criteria);
	}
}
