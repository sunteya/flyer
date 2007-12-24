/**
 * Created on 2007-9-26
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;

/**
 * @author Sunteya
 *
 */
public class CriteriaQueryWrapper implements CriteriaQuery {

	private static final long serialVersionUID = 7306082979833963362L;

	private CriteriaQuery query;

	public CriteriaQuery getQuery() {
		return query;
	}

	public void setQuery(CriteriaQuery query) {
		this.query = query;
	}

	public CriteriaQuery add(Criterion criterion) {
		return query.add(criterion);
	}

	public String getAlias() {
		return query.getAlias();
	}

	public boolean hasCriterion() {
		return query.hasCriterion();
	}

	public String qualifiedAlias(String alias) {
		return query.qualifiedAlias(alias);
	}

	public String qualifiedProperty(String name) {
		return query.qualifiedProperty(name);
	}

	public DetachedCriteria toCriteria() {
		return query.toCriteria();
	}

	public CriteriaQuery createCriteria(String associationPath)
			throws HibernateException {
		return query.createCriteria(associationPath);
	}

	public CriteriaQuery setProjection(Projection projection) {
		return query.setProjection(projection);
	}

	public CriteriaQuery createCriteria(String associationPath, String alias)
			throws HibernateException {
		return query.createCriteria(associationPath, alias);
	}
}
