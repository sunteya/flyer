/**
 * Created on 2007-7-16
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

/**
 * @author Sunteya
 *
 */
public class CriteriaAccessor extends CriteriaQueryWrapper {

	private static final long serialVersionUID = -6539310428471004802L;

	public CriteriaAccessor() {
	}

	public CriteriaAccessor(CriteriaQuery query) {
		setQuery(query);
	}

	// =====================================================
	// property
	// -----------------------------------------------------
	public CriteriaAccessor addEqualIfNotNull(String propertyName, Object value) {
		if(value != null) {
			add(Restrictions.eq(propertyName, value));
		}

		return this;
	}

	public CriteriaAccessor addEqualIfNotBlank(String propertyName, String value) {
		if(StringUtils.isNotBlank(value)) {
			addEqualIfNotNull(propertyName, value);
		}

		return this;
	}

	public CriteriaAccessor addLikeIfNotBlank(String propertyName, String value, MatchMode matchMode) {
		if(StringUtils.isNotBlank(value)) {
			add(Restrictions.like(propertyName, value, matchMode));
		}

		return this;
	}

	public CriteriaAccessor addRangeIfNotNull(String propertyName, Object start, Object end) {
		if(start != null) {
			add(Restrictions.ge(propertyName, start));
		}

		if(end != null) {
			add(Restrictions.le(propertyName, end));
		}

		return this;
	}

	public CriteriaAccessor addDateRangeIfNotNull(String propertyName, Date start, Date end) {
		if(start != null) {
			add(Restrictions.ge(propertyName, start));
		}

		if(end != null) {
			add(Restrictions.lt(propertyName, DateUtils.addDays(end, 1)));
		}

		return this;
	}


	// =====================================================
	// EntityProperty
	// -----------------------------------------------------
	public CriteriaAccessor addEntityPropertyEqualIfNotNull(String associationPath, String propertyName, Object value) {
		if(value != null) {
			createCriteria(associationPath).add(Restrictions.eq(propertyName, value));
		}

		return this;
	}


	public CriteriaAccessor addEntityPropertyEqualIfNotBlank(String associationPath, String propertyName, String value) {
		if(StringUtils.isNotBlank(value)) {
			addEntityPropertyEqualIfNotNull(associationPath, propertyName, value);
		}

		return this;
	}

	public CriteriaAccessor addEntityPropertyLikeIfNotBlank(String associationPath, String alias, String propertyName, String value, MatchMode matchMode) {
		if(StringUtils.isNotBlank(value)) {
			createCriteria(associationPath).add(Restrictions.like(propertyName, value, matchMode));
		}

		return this;
	}


	public CriteriaAccessor addEntitySubquery(CriteriaBuilder subquery, String associationPath, String alias) {
		if(subquery == null || !subquery.buildCriteria().hasCriterion()) {
			return this;
		}

		DetachedCriteria criteria = subquery.buildCriteria(qualifiedAlias(alias)).setProjection(Projections.id()).toCriteria();
		createCriteria(associationPath).add(Subqueries.propertyIn("id", criteria));
		return this;
	}

	public CriteriaAccessor addFastEntitySubquery(CriteriaBuilder subquery, String subalias, String associationPathInSub) {
		if(subquery == null || !subquery.buildCriteria().hasCriterion()) {
			return this;
		}

		CriteriaQuery query = subquery.buildCriteria(qualifiedAlias(subalias));
		query.createCriteria(associationPathInSub).add(Restrictions.eqProperty("id", qualifiedProperty("id")));
		query.setProjection(Projections.id());
		add(Subqueries.exists(query.toCriteria()));
		return this;
	}

	public CriteriaAccessor addInnerEntitySubquery(CriteriaBuilder subquery, String associationPath, String alias) {
		if(subquery == null || !subquery.buildCriteria().hasCriterion()) {
			return this;
		}

		subquery.buildCriteria(createCriteria(associationPath, qualifiedAlias(alias)));
		return this;
	}
}
