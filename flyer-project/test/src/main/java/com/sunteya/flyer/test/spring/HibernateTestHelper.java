/**
 * Created on 2006-12-11
 * Created by Sunteya
 */
package com.sunteya.flyer.test.spring;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author Sunteya
 *
 */
public class HibernateTestHelper extends HibernateDaoSupport {

	@SuppressWarnings("unchecked")
	public HibernateTestHelper save(Object entity) {
		getHibernateTemplate().save(entity);

		return this;
	}

	public HibernateTestHelper saveAll(Object... entities) {
		for (Object entity : entities) {
			getHibernateTemplate().save(entity);
		}

		return this;
	}

	public HibernateTestHelper flushAndClear() {
		getHibernateTemplate().flush();
		getHibernateTemplate().clear();

		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz, Serializable id) {
		return (T) getHibernateTemplate().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public HibernateTestHelper remove(Object entity) {
		getHibernateTemplate().delete(entity);

		return this;
	}

	public HibernateTestHelper removeAll(Object... entities) {
		for (Object entity : entities) {
			getHibernateTemplate().refresh(entity);
			getHibernateTemplate().delete(entity);
		}

		return this;
	}

	@SuppressWarnings("unchecked")
	public HibernateTestHelper update(Object entity) {
		getHibernateTemplate().update(entity);

		return this;
	}

	public HibernateTestHelper updateAll(Object... entities) {
		for (Object entity : entities) {
			getHibernateTemplate().update(entity);
		}

		return this;
	}

	public HibernateTestHelper deleteFormCriterias(DetachedCriteria... detachedCriterias) {
		for (final DetachedCriteria detachedCriteria : detachedCriterias) {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) {
					int deletedCount = 0;
					Criteria criteria = detachedCriteria
							.getExecutableCriteria(session);
					for (Object entity : criteria.list()) {
						session.delete(entity);
						deletedCount++;
					}

					if (logger.isInfoEnabled()) {
						logger.info("Deleted " + deletedCount
								+ " rows from criteria " + criteria.getClass());
					}

					return deletedCount;
				}
			});
		}

		return this;
	}


	public HibernateTestHelper deleteFromClasses(final Class<?>... classes) {
		for (final Class<?> clazz : classes) {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) {
					String name = clazz.getSimpleName();
					String hqlDelete = "delete " + name;
					int deletedCount = session.createQuery(hqlDelete)
							.executeUpdate();

					if (logger.isInfoEnabled()) {
						logger.info("Deleted " + deletedCount
								+ " rows from entity " + name);
					}

					return deletedCount;
				}
			});
		}

		return this;
	}

	public HibernateTestHelper deleteFromTableNames(final String... tableNames) {
		for (final String tableName : tableNames) {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) {
					String deleteSql = "delete from " + tableName;
					int deletedCount = session.createSQLQuery(deleteSql)
							.executeUpdate();

					if (logger.isInfoEnabled()) {
						logger.info("Deleted " + deletedCount
								+ " rows from table " + tableName);
					}

					return deletedCount;
				}
			});
		}

		return this;
	}

	public HibernateTestHelper executeScripts(final String... scripts) {
		for (final String script : scripts) {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) {
					int count = session.createSQLQuery(script).executeUpdate();

					if (logger.isInfoEnabled()) {
						logger.info("execute " + script + " finished, count="
								+ count);
					}

					return count;
				}
			});
		}

		return this;
	}
}
