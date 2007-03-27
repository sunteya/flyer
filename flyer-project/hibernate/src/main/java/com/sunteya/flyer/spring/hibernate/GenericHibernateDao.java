/**
 * Created on 2006-9-5
 * Created by Sunteya
 */
package com.sunteya.flyer.spring.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunteya.flyer.dao.GenericDao;
import com.sunteya.flyer.domain.Entity;
import com.sunteya.flyer.support.Pagination;
import com.sunteya.flyer.support.PaginationStrategy;
import com.sunteya.flyer.util.GenericUtils;

/**
 *
 * @author Sunteya
 * @email Sunteya@gmail.com
 */
public abstract class GenericHibernateDao<E extends Entity> extends HibernateDaoSupport
		implements GenericDao<E> {

	private Class<?> entityClass;

	public GenericHibernateDao() {
		entityClass = GenericUtils.getSuperClassTypeArgument(this, 0);
	}

	public GenericHibernateDao(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public Class<?> getEntityClass() {
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
	protected <Ojb> Ojb queryUniqueResultByCriteria(final DetachedCriteria criteria) {
		return (Ojb) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return criteria.getExecutableCriteria(session).uniqueResult();
			}
		}, true);
	}

	protected E findFirstEntityByCriteria(DetachedCriteria criteria) {
		List<E> rs = findEntitiesByCriteria(criteria);
		if (rs.isEmpty()) {
			return null;
		}

		return rs.get(0);
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

	protected Pagination<E> findPageByCriteria(DetachedCriteria criteria,
			int pageIndex, int pageSize) {
		return findPageByCriteria(criteria, pageIndex, pageSize, null);
	}

	@SuppressWarnings("unchecked")
	protected Pagination<E> findPageByCriteria(DetachedCriteria criteria,
			int pageIndex, int pageSize, Order order) {
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

	// =====================================================
	// Dao方法
	// -----------------------------------------------------
	@SuppressWarnings("unchecked")
	public E get(Serializable id) {
		return (E) getHibernateTemplate().get(entityClass, id);
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
}
