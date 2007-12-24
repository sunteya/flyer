/**
 * Created on 2007-7-23
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.impl;

import java.util.List;

import com.sunteya.flyer.validation.Validator;
import com.sunteya.flyer.validation.ValidatorProvider;

/**
 * @author Sunteya
 *
 */
public class NestedValidatorProvider implements ValidatorProvider {

	private String prefix;
	private ValidatorProvider provider;

	public NestedValidatorProvider(ValidatorProvider provider, String prefix) {
		this.prefix = prefix;
		this.provider = provider;
	}

	public List<Validator> findAllByPath(String fieldName) {
		return provider.findAllByPath(prefix + fieldName);
	}

	public boolean hasRequiredValidator(String path) {
		return provider.hasRequiredValidator(prefix + path);
	}

}
