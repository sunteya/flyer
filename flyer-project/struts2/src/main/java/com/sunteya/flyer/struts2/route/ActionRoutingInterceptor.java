/**
 * Created on 2007-6-21
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.route;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sunteya.flyer.web.route.ActionRoutingHolder;

/**
 * @author Sunteya
 *
 */
public class ActionRoutingInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 7696649741881314704L;

	@Override
	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		if(action instanceof ActionRoutingAware) {
			((ActionRoutingAware) action).setRoutingDeque(ActionRoutingHolder.getRoutingDeque());
		}

		return invocation.invoke();
	}
}
