/**
 * Created on 2007-6-27
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.validator.impl;

import org.apache.commons.lang.ObjectUtils;

import com.sunteya.flyer.validation.ValidationContext;
import com.sunteya.flyer.validation.validator.OptionalValidator;


/**
 * @author Sunteya
 *
 */
public class StringLengthValidator extends OptionalValidator {

	private Integer minLength;
	private Integer maxLength;
	
	public StringLengthValidator(Integer minLength, Integer maxLength) {
		setMaxLength(maxLength);
		setMinLength(minLength);
	}

	public StringLengthValidator(String path, Integer minLength, Integer maxLength) {
		setPath(path);
		setMaxLength(maxLength);
		setMinLength(minLength);
	}

	protected String getErrorCode(ValidationContext context) {
		String errorCode = super.getErrorCode(context);
		if(ObjectUtils.equals(getMaxLength(), getMinLength())) {
			return errorCode + ".length_only";
		}

		if(getMaxLength() == null) {
			return errorCode + ".min_only";
		}

		if(getMinLength() == null) {
			return errorCode + ".max_only";
		}

		return errorCode;
	}

	@Override
	protected boolean isVaildValue(Object test) {
		String value = (String) test;
		Integer length = value.length();
		if(getMinLength() != null && getMinLength() > length) {
			return false;
		}
		
		if(getMaxLength() != null && getMaxLength() < length) {
			return false;
		}
		
		return true;
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer mix) {
		this.minLength = mix;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer max) {
		this.maxLength = max;
	}
}
