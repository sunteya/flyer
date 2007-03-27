/**
 * Created on 2006-11-9
 * Created by Sunteya
 */
package com.sunteya.flyer.dao;

import java.io.Serializable;

import com.sunteya.flyer.domain.Entity;


/**
 * @author Sunteya
 *
 */
public interface GenericDao<T extends Entity> {

	T get(Serializable id);

	void removeById(Serializable id);

	void remove(T t);

	void save(T t);

	void update(T t);

	void saveOrUpdate(T t);
}
