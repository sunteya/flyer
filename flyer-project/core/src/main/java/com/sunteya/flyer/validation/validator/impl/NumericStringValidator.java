/**
 * Created on 2007-6-28
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.validator.impl;

import org.apache.commons.lang.StringUtils;

import com.sunteya.flyer.validation.validator.OptionalValidator;


/**
 * @author Sunteya
 *
 */
public class NumericStringValidator extends OptionalValidator {

	public NumericStringValidator(String fieldName) {
		setPath(fieldName);
	}

	@Override
	protected boolean isVaildValue(Object value) {
		return StringUtils.isNumeric(value.toString());
	}
}
