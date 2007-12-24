/**
 * Created on 2007-12-28
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.validator.impl;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

import com.sunteya.flyer.validation.validator.OptionalValidator;

/**
 * @author Sunteya
 *
 */
public abstract class StringValidatorSupport extends OptionalValidator {

	private String specialChars = "";
	private boolean numeric = false;
	private boolean uppercase = false;
	private boolean lowercase = false;

	@Override
	protected boolean isVaildValue(Object value) {
		String password = value.toString();
		for(int i = 0; i < password.length(); i++) {
			if(!isVaildChar(password.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	protected boolean isVaildChar(char ch) {
		if(isNumeric() && CharUtils.isAsciiNumeric(ch)) {
			return true;
		}

		if(isLowercase() && CharUtils.isAsciiAlphaLower(ch)) {
			return true;
		}

		if(isUppercase() && CharUtils.isAsciiAlphaUpper(ch)) {
			return true;
		}

		if(StringUtils.contains(getSpecialChars(), ch)) {
			return true;
		}


		return false;
	}

	public String getSpecialChars() {
		return specialChars;
	}

	public void setSpecialChars(String specialChars) {
		this.specialChars = specialChars;
	}

	public boolean isNumeric() {
		return numeric;
	}

	public void setNumeric(boolean numeric) {
		this.numeric = numeric;
	}

	public boolean isUppercase() {
		return uppercase;
	}

	public void setUppercase(boolean uppercase) {
		this.uppercase = uppercase;
	}

	public boolean isLowercase() {
		return lowercase;
	}

	public void setLowercase(boolean lowercase) {
		this.lowercase = lowercase;
	}

}