/**
 * Created on 2007-11-27
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.sunteya.flyer.i18n.FieldPathResolvable;
import com.sunteya.flyer.i18n.MessageCode;
import com.sunteya.flyer.validation.ErrorCodesResolver;
import com.sunteya.flyer.validation.FieldValidationAware;
import com.sunteya.flyer.validation.ValidationContext;
import com.sunteya.flyer.validation.ValidatorConfiguration;
import com.sunteya.flyer.validation.validator.impl.SimpleNameErrorCodesResolver;


/**
 * @author Sunteya
 *
 */
public class ValidateActionSupport extends I18nActionSupport implements FieldValidationAware {

	private ErrorCodesResolver errorCodesResolver = new SimpleNameErrorCodesResolver();
	private Map<String, List<String>> fieldErrors = new HashMap<String, List<String>>();
	public static String FIELD_NAME_KEY = "fieldName";

	protected ValidatorConfiguration validatorConfiguration = new ValidatorConfiguration();

	public void addFieldError(String path, String errorMessage) {
		List<String> fieldError = getFieldErrors().get(path);
		if(fieldError == null) {
			fieldError = new ArrayList<String>();
		}

		fieldError.add(errorMessage);
		getFieldErrors().put(path, fieldError);
	}

	public ValidationContext getValidationContext() {
		return new ValidationContextAdapter(errorCodesResolver, this, getValueStack());
	}

	public boolean hasFieldError(String path) {
		List<String> error = getFieldErrors().get(path);
		return CollectionUtils.isNotEmpty(error);
	}

	public void addFieldError(String path, MessageCode code) {
		Map<String, String> ctx = new HashMap<String, String>();
		ctx.put(FIELD_NAME_KEY, getPathName(path));

		code.getContexts().add(ctx);
		addFieldError(path, getMessage(code));
	}

	public String getPathName(String path) {
		return getMessage(resolvePathMessageCode(path));
	}

	public MessageCode resolvePathMessageCode(String path) {
		return resolveMessageCode(new FieldPathResolvable(this, path));
	}

	public Map<String, List<String>> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(Map<String, List<String>> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public boolean hasErrors() {
		for (List<String> error : fieldErrors.values()) {
			if(CollectionUtils.isNotEmpty(error)) {
				return true;
			}
		}
		return false;
	}

	public ValidatorConfiguration getValidatorConfiguration() {
		return validatorConfiguration;
	}

	public void setValidatorConfiguration(ValidatorConfiguration validatorConfiguration) {
		this.validatorConfiguration = validatorConfiguration;
	}
}
