/**
 * Created on 2007-7-20
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.builder;

import org.apache.commons.beanutils.PropertyUtils;

import com.sunteya.flyer.validation.Validator;
import com.sunteya.flyer.validation.ValidatorAppendable;
import com.sunteya.flyer.validation.validator.AbstractValidator;

/**
 * @author Sunteya
 *
 */
public class PropertyValidatorBuilder extends ValidatorBuilder {
	private String propertyName;

	public PropertyValidatorBuilder(String propertyName, ValidatorAppendable appendable) {
		super(appendable);
		this.propertyName = propertyName;
	}

	@Override
	public ValidatorBuilder property(String propertyName) {
		return new PropertyValidatorBuilder(this.propertyName + "." +  propertyName, appendable);
	}

	@Override
	public ValidatorBuilder add(Validator validator) {
		if(validator instanceof AbstractValidator) {
			((AbstractValidator) validator).setPath(propertyName);
		} else {
			try {
				if(PropertyUtils.isWriteable(validator, "fieldName")) {
					PropertyUtils.setProperty(validator, "fieldName", propertyName);
				}
			} catch (Exception skip) { skip.printStackTrace(); }
		}

		super.add(validator);
		return this;
	}
}