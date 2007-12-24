/**
 * Created on 2007-8-22
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.sunteya.flyer.domain.Entity;

/**
 * @author Sunteya
 *
 */
public class EvictAccessor {

	private SessionFactory sessionFactory;

	public void evict(Entity entity) throws HibernateException {
		evict(Hibernate.getClass(entity), entity.getIdentityCode());
	}

	public void evict(Class<?> persistentClass, Serializable id) throws HibernateException {
		sessionFactory.evict(persistentClass, id);
	}

	public void evict(String entityName, Serializable id) throws HibernateException {
		sessionFactory.evictEntity(entityName, id);
	}

	//~ EvictClass ------------------------------------------------------------------------------------
	public void evictClass(Entity entity) throws HibernateException {
		evictClass(Hibernate.getClass(entity));
	}

	public void evictClass(Class<?> persistentClass) throws HibernateException {
		sessionFactory.evict(persistentClass);
	}

	public void evictClass(String entityName) throws HibernateException {
		sessionFactory.evictEntity(entityName);
	}

	//~ EvictCollection ------------------------------------------------------------------------------
	public void evictCollection(Entity entity, String property) throws HibernateException {
		evictCollection(Hibernate.getClass(entity), property, entity.getIdentityCode());
	}

	public void evictCollection(Class<?> persistentClass, String property, Serializable id) throws HibernateException {
		evictCollection(persistentClass.getName() + "." + property, id);
	}

	public void evictCollection(String roleName, Serializable id) throws HibernateException {
		sessionFactory.evictCollection(roleName, id);
	}

	//~ EvictAllCollection ------------------------------------------------------------------------------
	public void evictAllCollection(Entity entity, String property) throws HibernateException {
		evictAllCollection(Hibernate.getClass(entity), property);
	}

	public void evictAllCollection(Class<?> persistentClass, String property) throws HibernateException {
		evictAllCollection(persistentClass.getName() + "." + property);
	}

	public void evictAllCollection(String roleName) throws HibernateException {
		sessionFactory.evictCollection(roleName);
	}

	//~ EvictQueries ----------------------------------------------------------------------------------
	public void evictQueries() throws HibernateException {
		sessionFactory.evictQueries();
	}

	public void evictQueries(String cacheRegion) throws HibernateException {
		sessionFactory.evictQueries(cacheRegion);
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
