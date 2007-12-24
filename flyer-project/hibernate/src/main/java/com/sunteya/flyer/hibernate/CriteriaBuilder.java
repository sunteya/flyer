/**
 *
 */
package com.sunteya.flyer.hibernate;



/**
 * @author Sunteya
 *
 */
public interface CriteriaBuilder {
	CriteriaQuery buildCriteria();
	CriteriaQuery buildCriteria(String alias);
	CriteriaQuery buildCriteria(CriteriaQuery query);
}