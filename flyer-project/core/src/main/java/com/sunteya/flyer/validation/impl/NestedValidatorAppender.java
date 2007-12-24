/**
 * Created on 2007-7-23
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.impl;

import com.sunteya.flyer.validation.Validator;
import com.sunteya.flyer.validation.ValidatorAppendable;
import com.sunteya.flyer.validation.validator.NestedFieldValidator;

/**
 * @author Sunteya
 *
 */
public class NestedValidatorAppender implements ValidatorAppendable {

	private String prefix;
	private ValidatorAppendable appendable;

	public NestedValidatorAppender(String prefix, ValidatorAppendable appendable) {
		this.appendable = appendable;
		this.prefix = prefix;
	}

	public void addValidator(Validator validator) {
		appendable.addValidator(new NestedFieldValidator(validator, prefix));
	}
}
