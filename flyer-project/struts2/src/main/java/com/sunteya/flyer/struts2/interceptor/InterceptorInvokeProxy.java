/**
 * Created on 2008-1-2
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.interceptor;

import javax.servlet.Filter;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author Sunteya
 *
 */
public class InterceptorInvokeProxy extends AbstractInterceptor {

	private static final long serialVersionUID = -2002448188649518104L;
	private Filter[] filters;

	public String intercept(ActionInvocation invocation) throws Exception {
		VirtualFilterChain chain = new VirtualFilterChain(invocation, getFilters());
		return chain.invoke();
	}

	public Filter[] getFilters() {
		return filters;
	}

	public void setFilters(Filter[] filters) {
		this.filters = filters;
	}
}