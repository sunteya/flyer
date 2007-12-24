/**
 * Created on 2007-6-27
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.validator;

import com.sunteya.flyer.i18n.MessageCode;
import com.sunteya.flyer.validation.ObjectValidatable;
import com.sunteya.flyer.validation.ValidationContext;
import com.sunteya.flyer.validation.Validator;

/**
 * @author Sunteya
 *
 */
public abstract class AbstractValidator implements Validator, ObjectValidatable {

	protected String path;

	public String[] getRelativePaths() {
		return new String[] { getPath() };
	}

	public boolean isReady(ValidationContext context) {
		for (String relativePath : getRelativePaths()) {
			if(context.hasFieldError(relativePath)) {
				return false;
			}
		}

		return true;
	}

	public void validate(ValidationContext context) {
		Object value = context.getValue(getPath());
		if(!isVaild(value)) {
			addDefaultFieldError(context);
		}
	}

	public boolean isVaild(Object value) {
		throw new UnsupportedOperationException();
	}

	protected void addDefaultFieldError(ValidationContext context) {
		MessageCode code = context.resolvePathMessageCode(getPath());
		String errorCode = getErrorCode(context);
		code.appendCodesPrefix(errorCode + ".");
		code.getCodes().add(errorCode);

		code.getContexts().add(this);
		context.addFieldError(getPath(), code);
	}

	protected String getErrorCode(ValidationContext context) {
		return context.getErrorCode(this);
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}