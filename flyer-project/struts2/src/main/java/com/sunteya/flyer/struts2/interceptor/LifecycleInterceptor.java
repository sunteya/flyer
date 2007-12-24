/**
 * Created on 2007-12-3
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sunteya.flyer.struts2.action.ActionLifecycle;

/**
 * @author Sunteya
 *
 */
public abstract class LifecycleInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		if (action instanceof ActionLifecycle) {
			doIntercept((ActionLifecycle) action);
		}

		return invocation.invoke();
	}

	protected abstract void doIntercept(ActionLifecycle action) throws Exception;
}
