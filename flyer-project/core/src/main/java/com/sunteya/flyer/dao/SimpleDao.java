/**
 * Created on 2007-8-30
 * Created by Sunteya
 */
package com.sunteya.flyer.dao;

import java.io.Serializable;

import com.sunteya.flyer.domain.Entity;

/**
 * @author Sunteya
 *
 */
public interface SimpleDao {
	<E extends Entity> E get(Class<E> clazz, Serializable id);
	void update(Entity entity);
	void save(Entity entity);
	void remove(Entity entity);
	void refresh(Entity entity);
}
