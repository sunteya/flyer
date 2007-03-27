/**
 * Created on 2007-3-20
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.validator;

import org.springframework.validation.Errors;

import com.sunteya.flyer.util.GenericUtils;

/**
 * @author Sunteya
 *
 * @param <T>
 */
public abstract class GenericValidator<T> {

	protected Class<T> commandClass;

	public GenericValidator() {
		commandClass = GenericUtils.getSuperClassTypeArgument(this, 0);
	}

	public GenericValidator(Class<T> commandClass) {
		super();
		this.commandClass = commandClass;
	}

	@SuppressWarnings("unchecked")
	public final boolean supports(Class clazz) {
		return commandClass.isAssignableFrom(clazz);
	}

	@SuppressWarnings("unchecked")
	public final void validate(Object obj, Errors errors) {
		T command = (T) obj;
		doValidate(command, errors);
	}

	protected abstract void doValidate(T command, Errors errors);
}