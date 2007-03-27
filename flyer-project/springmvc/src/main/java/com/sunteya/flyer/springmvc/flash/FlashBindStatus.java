/**
 * Created on 2007-4-9
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.flash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.support.RequestContext;

/**
 * @author Sunteya
 *
 */
public class FlashBindStatus {

	public static FlashBindStatus newInstance(RequestContext requestContext, Map<String, Object> flashModel, String path) {
		return new FlashBindStatus(requestContext, flashModel, path);
	}

	private RequestContext requestContext;
	private Map<String, Object> flashModel;
	private String path;

	private String expression;
	private Errors errors;

	private List<ObjectError> objectErrors;
	private Object value;

	public FlashBindStatus(RequestContext requestContext, Map<String, Object> flashModel, String path)
			throws IllegalStateException {
		this.requestContext = requestContext;
		this.flashModel = flashModel;
		this.path = path;

		this.objectErrors = new ArrayList<ObjectError>();
		if(this.flashModel == null) {
			this.flashModel = new HashMap<String, Object>();
		}
		init();
	}

	@SuppressWarnings("unchecked")
	private void init() {
		// determine name of the object and property
		String beanName = null;
		int dotPos = path.indexOf('.');
		if (dotPos == -1) {
			// property not set, only the object itself
			beanName = path;
			this.expression = null;
		} else {
			beanName = path.substring(0, dotPos);
			this.expression = path.substring(dotPos + 1);
		}

		this.errors = getError(beanName);

		if (this.errors != null) {
			if (this.expression != null) {
				if ("*".equals(this.expression)) {
					this.objectErrors = this.errors.getAllErrors();
				} else if (this.expression.endsWith("*")) {
					this.objectErrors = this.errors
							.getFieldErrors(this.expression);
				} else {
					this.objectErrors = this.errors
							.getFieldErrors(this.expression);
					this.value = this.errors.getFieldValue(this.expression);
				}
			} else {
				this.objectErrors = this.errors.getGlobalErrors();
			}
		}
	}

	protected Object getFlashModelObject(String beanName) {
		return flashModel.get(beanName);
	}

	private Errors getError(String beanName) {
		return (Errors) getFlashModelObject(BindingResult.MODEL_KEY_PREFIX + beanName);
	}

	public boolean isError() {
		return !objectErrors.isEmpty();
	}

	public String getErrorMessage() {
		String[] errorMessages = getErrorMessages();
		return (errorMessages.length > 0 ? errorMessages[0] : "");
	}

	public String[] getErrorMessages() {
		List<String> errorMessages = new ArrayList<String>();

		for(ObjectError error : this.objectErrors) {
			String errorMessage = this.requestContext.getMessage(error);
			errorMessages.add(errorMessage);
		}

		return errorMessages.toArray(new String[0]);
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public String getExpression() {
		return expression;
	}

	public Object getValue() {
		return this.value;
	}
}
