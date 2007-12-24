/**
 * Created on 2008-1-2
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

public class VirtualFilterChain implements FilterChain {

	private ActionInvocation invocation;
	private Filter[] filters;
	int position = 0;
	private String resultCode = Action.NONE;

	public VirtualFilterChain(ActionInvocation invocation, Filter[] additionalFilters) {
		setInvocation(invocation);
		this.filters = additionalFilters;
	}

	public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		position++;
		if(position == filters.length) {
			filters[position - 1].doFilter(request, response, new FilterChain() {
				public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
					try {
						setResultCode(getInvocation().invoke());
					} catch (Exception e) {
						throw new NestException(e);
					}
				}
			});
		} else {
			filters[position - 1].doFilter(request, response, this);
		}
	}

	public String invoke() throws Exception {
		ActionContext invocationContext = getInvocation().getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) invocationContext.get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) invocationContext.get(ServletActionContext.HTTP_RESPONSE);

		try {
			doFilter(request, response);
		} catch (NestException e) {
			throw (Exception) e.getCause();
		}

		return getResultCode();
	}



	private class NestException extends RuntimeException {
		private static final long serialVersionUID = 1021877429799060371L;

		public NestException(Throwable cause) {
			super(cause);
		}
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	private void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	private String getResultCode() {
		return resultCode;
	}

	private void setInvocation(ActionInvocation invocation) {
		this.invocation = invocation;
	}

	private ActionInvocation getInvocation() {
		return invocation;
	}
}