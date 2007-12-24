/**
 * Created on 2007-7-23
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.impl;

import com.sunteya.flyer.i18n.MessageCode;
import com.sunteya.flyer.validation.ValidationContext;

/**
 * @author Sunteya
 *
 */
public class NestedValidationContext implements ValidationContext {

	private ValidationContext context;
	private String prefix;

	public NestedValidationContext(ValidationContext context, String prefix) {
		this.context = context;
		this.prefix = prefix;
	}

	public String fullFieldName(String path) {
		return prefix + path;
	}

	// =====================================================
	// Delegating
	// -----------------------------------------------------
	public void addFieldError(String fieldName, String errorMessage) {
		context.addFieldError(fullFieldName(fieldName), errorMessage);
	}

	public void addFieldError(String path, MessageCode code) {
		context.addFieldError(fullFieldName(path), code);
	}

	public Object getValue(String fieldName) {
		return context.getValue(fullFieldName(fieldName));
	}

	public boolean hasErrors() {
		return context.hasErrors();
	}

	public boolean hasFieldError(String fieldName) {
		return context.hasFieldError(fullFieldName(fieldName));
	}

	public String getErrorCode(Object cause) {
		return context.getErrorCode(cause);
	}

	public String getMessage(MessageCode code) {
		return context.getMessage(code);
	}

	public MessageCode resolvePathMessageCode(String path) {
		return context.resolvePathMessageCode(fullFieldName(path));
	}

	public MessageCode resolveMessageCode(Object obj) {
		return context.resolveMessageCode(obj);
	}
}
