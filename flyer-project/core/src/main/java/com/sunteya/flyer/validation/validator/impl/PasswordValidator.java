/**
 * Created on 2007-12-28
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.validator.impl;



/**
 * @author Sunteya
 *
 */
public class PasswordValidator extends StringValidatorSupport {

	public PasswordValidator() {
		setPath(null);
	}

	public PasswordValidator(String path) {
		setPath(path);

		setSpecialChars("!@#$%^&_");
		setLowercase(true);
		setNumeric(true);
		setUppercase(true);
	}
}
