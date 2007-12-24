/**
 * Created on 2007-7-20
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.builder;

import java.util.Collection;

import com.sunteya.flyer.validation.Validator;
import com.sunteya.flyer.validation.ValidatorAppendable;

/**
 * @author Sunteya
 *
 */
public class ValidatorBuilder implements ValidatorAppendable {
	protected ValidatorAppendable appendable;

	public ValidatorBuilder(ValidatorAppendable appendable) {
		this.appendable = appendable;
	}

	public ValidatorBuilder property(String propertyName) {
		return new PropertyValidatorBuilder(propertyName, appendable);
	}

	public ValidatorBuilder add(Validator validator) {
		appendable.addValidator(validator);
		return this;
	}

	public ValidatorBuilder addAll(Collection<? extends Validator> validators) {
		for (Validator fieldValidator : validators) {
			add(fieldValidator);
		}

		return this;
	}

	public ValidatorBuilder addAll(Validator...validators) {
		for (Validator fieldValidator : validators) {
			add(fieldValidator);
		}

		return this;
	}

	public void addValidator(Validator validator) {
		add(validator);
	}
}
