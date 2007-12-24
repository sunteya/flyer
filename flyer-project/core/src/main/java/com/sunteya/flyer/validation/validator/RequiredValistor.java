/**
 * Created on 2007-7-13
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.validator;

import com.sunteya.flyer.validation.ValidatorProvider;


/**
 * @author Sunteya
 *
 */
public abstract class RequiredValistor extends AbstractValidator {

	public boolean isRequired(ValidatorProvider provider) {
		return true;
	}
}