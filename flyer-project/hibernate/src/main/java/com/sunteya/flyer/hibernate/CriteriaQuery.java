/**
 * Created on 2007-9-26
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;

/**
 * @author Sunteya
 *
 */
public interface CriteriaQuery extends Serializable {

	CriteriaQuery add(Criterion criterion);
	CriteriaQuery setProjection(Projection projection);
	CriteriaQuery createCriteria(String associationPath) throws HibernateException;
	CriteriaQuery createCriteria(String associationPath, String alias) throws HibernateException;

	boolean hasCriterion();

	DetachedCriteria toCriteria();

	String getAlias();
	String qualifiedAlias(String alias);
	String qualifiedProperty(String name);
}