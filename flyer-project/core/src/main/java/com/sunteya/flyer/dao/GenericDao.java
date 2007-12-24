/**
 * Created on 2006-11-9
 * Created by Sunteya
 */
package com.sunteya.flyer.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.sunteya.flyer.domain.Entity;


/**
 * @author Sunteya
 *
 */
public interface GenericDao<T extends Entity> {

	T get(Serializable id);

	T findById(Serializable id);

	List<T> findAllByMap(Map<String, Object> param, boolean ignoreCase);

	void removeById(Serializable id);

	void remove(T t);

	void save(T t);

	void update(T t);

	void saveOrUpdate(T t);

	List<T> findAll();

	List<T> findAllByIds(Collection<? extends Serializable> ids);
}
