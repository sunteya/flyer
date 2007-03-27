/**
 * Created on 2006-9-8
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author 诸南敏
 * @email zhunm@dep5.com
 */
public abstract class AbstractValidator<T> extends GenericValidator<T> implements Validator {

	boolean ifErrorContinue = false;

	public AbstractValidator() {
		super();
	}

	public AbstractValidator(Class<T> commandClass) {
		super(commandClass);
	}

	@Override
	protected void doValidate(T command, Errors errors) {
		validateRequired(command, errors);
		if (ifErrorContinue || !errors.hasErrors()) {
			validateContent(command, errors);
		}
	}

	public void setIfErrorContinue(boolean ifErrorContinue) {
		this.ifErrorContinue = ifErrorContinue;
	}

	protected void validateRequired(T command, Errors errors) {
	}

	protected void validateContent(T command, Errors errors) {
	}
}
