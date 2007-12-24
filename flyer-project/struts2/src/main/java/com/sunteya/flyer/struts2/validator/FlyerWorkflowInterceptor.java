/**
 * Created on 2007-11-30
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.validator;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sunteya.flyer.validation.FieldValidationAware;

/**
 * @author Sunteya
 *
 */
public class FlyerWorkflowInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -4869490032220748256L;
	private String inputResultName = Action.INPUT;

    public String intercept(ActionInvocation invocation) throws Exception {
        Object action = invocation.getAction();

        if (action instanceof FieldValidationAware) {
        	FieldValidationAware fieldValidationAware = (FieldValidationAware) action;

            if (fieldValidationAware.hasErrors()) {
            	return inputResultName;
            }
        }

        return invocation.invoke();
    }

    public void setInputResultName(String inputResultName) {
		this.inputResultName = inputResultName;
	}

	public String getInputResultName() {
		return inputResultName;
	}

}
