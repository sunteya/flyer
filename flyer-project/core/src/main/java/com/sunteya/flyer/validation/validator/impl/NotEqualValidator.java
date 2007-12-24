package com.sunteya.flyer.validation.validator.impl;

import org.apache.commons.lang.ObjectUtils;

import com.sunteya.flyer.validation.ValidationContext;
import com.sunteya.flyer.validation.validator.MultiPathValidator;

public class NotEqualValidator extends MultiPathValidator {

	public NotEqualValidator(String path, String sourcePath) {
		setPath(path);
		setOtherPath(sourcePath);
	}

	public NotEqualValidator(String sourcePath) {
		setOtherPath(sourcePath);
	}

	@Override
	public void validate(ValidationContext context) {
		Object value = context.getValue(getPath());
		Object source = context.getValue(getOtherPath());

		if(ObjectUtils.equals(source, value)) {
			addDefaultFieldError(context);
		}
	}
}
