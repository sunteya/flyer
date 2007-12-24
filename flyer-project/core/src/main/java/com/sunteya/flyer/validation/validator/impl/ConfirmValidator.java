/**
 * Created on 2007-7-23
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.validator.impl;

import org.apache.commons.lang.ObjectUtils;

import com.sunteya.flyer.validation.ValidationContext;
import com.sunteya.flyer.validation.validator.MultiPathValidator;

/**
 * @author Sunteya
 *
 */
public class ConfirmValidator extends MultiPathValidator {

	public ConfirmValidator(String path, String sourcePath) {
		setPath(path);
		setSourcePath(sourcePath);
	}

	public ConfirmValidator(String sourcePath) {
		setSourcePath(sourcePath);
	}

	@Override
	public void validate(ValidationContext context) {
		Object confirm = context.getValue(getPath());
		Object source = context.getValue(getOtherPath());
		if(!ObjectUtils.equals(source, confirm)) {
			addDefaultFieldError(context);
		}
	}

	public String getSourcePath() {
		return getOtherPath();
	}

	public void setSourcePath(String sourcePath) {
		setOtherPath(sourcePath);
	}
}
