/**
 * Created on 2007-8-30
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate.spring;

import java.io.Serializable;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sunteya.flyer.dao.SimpleDao;
import com.sunteya.flyer.domain.Entity;

/**
 * @author Sunteya
 *
 */
public class SimpleHibernateDao extends HibernateDaoSupport implements SimpleDao {

	public void save(Entity entity) {
		getHibernateTemplate().save(entity);
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> T get(Class<T> clazz, Serializable id) {
		return (T) getHibernateTemplate().get(clazz, id);
	}

	public void remove(Entity entity) {
		getHibernateTemplate().delete(entity);
	}

	public void update(Entity entity) {
		getHibernateTemplate().update(entity);
	}

	public void refresh(Entity entity) {
		getHibernateTemplate().refresh(entity);
	}
}