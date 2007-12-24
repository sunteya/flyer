/**
 * Created on 2008-1-2
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.result;

import java.util.Deque;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.sunteya.flyer.web.route.ActionRouting;
import com.sunteya.flyer.web.route.ActionRoutingHolder;

/**
 * @author Sunteya
 *
 */
public abstract class AbstractResult implements Result {

	private static final long serialVersionUID = -7261944020676778495L;

	public static final String DEFAULT_PARAM = StrutsResultSupport.DEFAULT_PARAM;

	private String location;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private Deque<ActionRouting> routingQeque;

	private ActionInvocation actionInvocation;

	private ServletContext servletContext;

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void execute(ActionInvocation invocation) throws Exception {
	    prepare(invocation);
		execute();
	}

	protected void prepare(ActionInvocation invocation) {
		ActionContext ctx = invocation.getInvocationContext();
		setActionInvocation(invocation);
		setRequest((HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST));
		setResponse((HttpServletResponse) ctx.get(StrutsStatics.HTTP_RESPONSE));
		setServletContext((ServletContext) ctx.get(StrutsStatics.SERVLET_CONTEXT));
		setRoutingQeque(ActionRoutingHolder.getRoutingDeque());
	}

	protected abstract void execute() throws Exception;

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public void setActionInvocation(ActionInvocation invocation) {
		this.actionInvocation = invocation;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public ActionInvocation getActionInvocation() {
		return actionInvocation;
	}

	public Deque<ActionRouting> getRoutingQeque() {
		return routingQeque;
	}

	public void setRoutingQeque(Deque<ActionRouting> routingQeque) {
		this.routingQeque = routingQeque;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}