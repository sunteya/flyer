/**
 *
 */
package com.sunteya.flyer.hibernate;


/**
 * @author Sunteya
 *
 */
public class EmptyCriteriaBuilder implements CriteriaBuilder {

	private Class<?> entityClass;

	public EmptyCriteriaBuilder(Class<?> clazz) {
		this.entityClass = clazz;
	}

	public CriteriaQuery buildCriteria() {
		return new DetachedCriteriaAdaptor(entityClass);
	}

	public CriteriaQuery buildCriteria(String alias) {
		return new DetachedCriteriaAdaptor(entityClass, alias);
	}

	public CriteriaQuery buildCriteria(CriteriaQuery query) {
		return query;
	}

	public boolean hasCriterion() {
		return false;
	}
}
