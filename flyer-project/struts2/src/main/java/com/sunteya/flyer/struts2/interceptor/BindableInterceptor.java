/**
 * Created on 2007-9-11
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author Sunteya
 *
 */
public class BindableInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 8315099048074694730L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		if(action instanceof Bindable) {
			((Bindable) action).bind();
		}

		return invocation.invoke();
	}
}
