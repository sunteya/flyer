/**
 * Created on 2007-11-28
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.action;

import com.opensymphony.xwork2.util.ValueStack;
import com.sunteya.flyer.i18n.MessageCode;
import com.sunteya.flyer.validation.ErrorCodesResolver;
import com.sunteya.flyer.validation.ValidationContext;

/**
 * @author Sunteya
 *
 */
public class ValidationContextAdapter implements ValidationContext {

	private ErrorCodesResolver errorCodesResolver;
	private ValidateActionSupport validateActionSupport;
	private ValueStack valueStack;


	public ValidationContextAdapter(ErrorCodesResolver errorCodesResolver,
			ValidateActionSupport validateActionSupport, ValueStack valueStack) {
		this.errorCodesResolver = errorCodesResolver;
		this.validateActionSupport = validateActionSupport;
		this.valueStack = valueStack;
	}

	public Object getValue(String path) {
        boolean pop = false;

        if (!valueStack.getRoot().contains(getRoot())) {
            valueStack.push(getRoot());
            pop = true;
        }

        Object retVal = valueStack.findValue(path);

        if (pop) {
            valueStack.pop();
        }

        return retVal;
	}

	protected Object getRoot() {
		return validateActionSupport;
	}

	public MessageCode resolvePathMessageCode(String path) {
		return validateActionSupport.resolvePathMessageCode(path);
	}

	public String getErrorCode(Object cause) {
		return errorCodesResolver.resolveErrorCode(cause);
	}

	public MessageCode resolveMessageCode(Object obj) {
		return validateActionSupport.resolveMessageCode(obj);
	}

	public void addFieldError(String path, String errorMessage) {
		validateActionSupport.addFieldError(path, errorMessage);
	}

	public void addFieldError(String path, MessageCode code) {
		validateActionSupport.addFieldError(path, code);
	}

	public boolean hasFieldError(String path) {
		return validateActionSupport.hasFieldError(path);
	}

	public String getMessage(MessageCode code) {
		return validateActionSupport.getMessage(code);
	}

	public boolean hasErrors() {
		return validateActionSupport.hasErrors();
	}
}
