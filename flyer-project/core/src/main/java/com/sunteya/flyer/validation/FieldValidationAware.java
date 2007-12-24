/**
 * Created on 2007-7-19
 * Created by Sunteya
 */
package com.sunteya.flyer.validation;

import com.sunteya.flyer.i18n.MessageCode;

/**
 * @author Sunteya
 *
 */
public interface FieldValidationAware {
	boolean hasFieldError(String path);
	boolean hasErrors();
	void addFieldError(String fieldPath, String errorMessage);
	void addFieldError(String path, MessageCode code);
}
