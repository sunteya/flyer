/**
 * Created on 2007-9-26
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;

/**
 * @author Sunteya
 *
 */
public class DetachedCriteriaAdaptor implements CriteriaQuery {

	private static final long serialVersionUID = 6704238997219451894L;

	private Map<String, CriteriaQuery> associationCriterias = new HashMap<String, CriteriaQuery>();
	private DetachedCriteria criteria;
	private int criterionCount;

	public DetachedCriteriaAdaptor(Class<?> clazz) {
		this(clazz, DetachedCriteria.ROOT_ALIAS);
	}

	public DetachedCriteriaAdaptor(Class<?> clazz, String alias) {
		this(DetachedCriteria.forClass(clazz, alias));
	}

	public DetachedCriteriaAdaptor(DetachedCriteria criteria) {
		this.criteria = criteria;
	}

	public CriteriaQuery add(Criterion criterion) {
		increaseCriterionCount();
		criteria.add(criterion);
		return this;
	}

	public CriteriaQuery setProjection(Projection projection) {
		criteria.setProjection(projection);
		return this;
	}

	// =====================================================
	// Alias
	// -----------------------------------------------------
	public String getAlias() {
		return criteria.getAlias();
	}

	public String qualifiedAlias(String alias) {
		return getAlias() + "_" + alias;
	}

	public String qualifiedProperty(String name) {
		return getAlias() + "." + name;
	}

	// =====================================================
	// associationPath
	// -----------------------------------------------------
	public CriteriaQuery createCriteria(String associationPath) throws HibernateException {
		increaseCriterionCount();

		if(associationCriterias.containsKey(associationPath)) {
			return associationCriterias.get(associationPath);
		}

		DetachedCriteria pathCriteria = criteria.createCriteria(associationPath);
		CriteriaQuery query = new DetachedCriteriaAdaptor(pathCriteria);
		associationCriterias.put(associationPath, query);
		return query;
	}

	public CriteriaQuery createCriteria(String associationPath, String alias) throws HibernateException {
		increaseCriterionCount();
		DetachedCriteria pathCriteria = criteria.createCriteria(associationPath, alias);
		CriteriaQuery query = new DetachedCriteriaAdaptor(pathCriteria);
		return query;
	}

	// =====================================================
	// Criterion
	// -----------------------------------------------------
	public boolean hasCriterion() {
		return criterionCount > 0;
	}

	public DetachedCriteria toCriteria() {
		return criteria;
	}

	private void increaseCriterionCount() {
		criterionCount++;
	}
}
