package com.sunteya.flyer.validation.validator.impl;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

import com.sunteya.flyer.validation.validator.RequiredValistor;

public class CollectionNotEmptyValidator extends RequiredValistor {

	public CollectionNotEmptyValidator(String path) {
		setPath(path);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean isVaild(Object value) {
		return CollectionUtils.isNotEmpty((Collection) value);
	}
}
