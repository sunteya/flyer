/**
 * Created on 2007-12-27
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.validator.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sunteya.flyer.dao.GenericDao;
import com.sunteya.flyer.domain.Entity;
import com.sunteya.flyer.validation.ValidationContext;
import com.sunteya.flyer.validation.validator.MultiPathValidator;

/**
 * @author Sunteya
 *
 */
public class EntityUniqueValidator extends MultiPathValidator {

	public static final String ID_RPOPERTY_NAME = "identityCode";

	private GenericDao<? extends Entity> genericDao;
	private boolean ignoreCase = false;


	public EntityUniqueValidator(GenericDao<? extends Entity> genericDao) {
		this(null, genericDao);
	}

	public EntityUniqueValidator(GenericDao<? extends Entity> genericDao, String... otherPath) {
		this(null, genericDao, otherPath);
	}

	public EntityUniqueValidator(String path, GenericDao<? extends Entity> genericDao, String... otherPath) {
		setPath(path);
		setOtherPaths(otherPath);
		setGenericDao(genericDao);
	}

	public EntityUniqueValidator ignoreCase() {
		setIgnoreCase(true);
		return this;
	}

	public void validate(ValidationContext context) {
		Map<String, Object> param = new HashMap<String, Object>();
		for (String path : getRelativePaths()) {
			String propertyName = StringUtils.contains(path, ".") ? StringUtils.substringAfterLast(path, ".") : path;
			Object value = context.getValue(path);

			param.put(propertyName, value);
		}
		String idPropertyName = StringUtils.contains(path, ".") ? StringUtils.substringBeforeLast(path, ".") + "." + ID_RPOPERTY_NAME : ID_RPOPERTY_NAME;
		Serializable id = (Serializable) context.getValue(idPropertyName);

		if(!isVaild(param, id)) {
			addDefaultFieldError(context);
		}
	}

	protected boolean isVaild(Map<String, Object> param, Serializable thisId) {
		List<? extends Entity> rs = getGenericDao().findAllByMap(param, ignoreCase);
		if(rs.isEmpty()) {
			return true;
		}
		if(rs.size() == 1 && rs.get(0).getIdentityCode().equals(thisId)) {
			return true;
		}

		return false;
	}

	public void setGenericDao(GenericDao<?> genericDao) {
		this.genericDao = genericDao;
	}

	public GenericDao<?> getGenericDao() {
		return genericDao;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}
}
