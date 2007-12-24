/**
 * Created on 2007-6-28
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.validator;

import com.sunteya.flyer.validation.ValidatorProvider;


/**
 * @author Sunteya
 *
 */
public abstract class OptionalValidator extends AbstractValidator {

	public boolean isRequired(ValidatorProvider provider) {
		return false;
	}

	@Override
	public boolean isVaild(Object value) {
		if(value == null) {
			return true;
		}

		return isVaildValue(value);
	}

	protected boolean isVaildValue(Object value) {
		throw new UnsupportedOperationException();
	}
}