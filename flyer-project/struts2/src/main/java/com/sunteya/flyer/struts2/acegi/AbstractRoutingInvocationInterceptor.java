/**
 * Created on 2007-8-20
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.acegi;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.intercept.AbstractSecurityInterceptor;
import org.acegisecurity.intercept.InterceptorStatusToken;
import org.acegisecurity.intercept.ObjectDefinitionSource;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.sunteya.flyer.acegi.web.RoutingInvocation;
import com.sunteya.flyer.acegi.web.RoutingInvocationDefinitionSource;
import com.sunteya.flyer.web.route.ActionRouting;
import com.sunteya.flyer.web.route.ActionRoutingHolder;

/**
 * @author Sunteya
 *
 */
public abstract class AbstractRoutingInvocationInterceptor extends AbstractSecurityInterceptor implements Interceptor {

	private RoutingInvocationDefinitionSource objectDefinitionSource;
	private boolean observeOncePerRequest = true;

	public void init() {
	}

	public void destroy() {
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class getSecureObjectClass() {
		return RoutingInvocation.class;
	}

	@Override
	public ObjectDefinitionSource obtainObjectDefinitionSource() {
		return getObjectDefinitionSource();
	}

	protected String getInterceptorAppliedKey() {
		return getClass().getName() + "_interceptorApplied";
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
		ActionRouting routing = ActionRoutingHolder.peekLastRouting();

        if (request.getAttribute(getInterceptorAppliedKey()) != null && isObserveOncePerRequest()) {
        		return invocation.invoke();
        } else {
        	request.setAttribute(getInterceptorAppliedKey(), Boolean.TRUE);

        	RoutingInvocation ri = buildRoutingInvocation(invocation, routing);
        	InterceptorStatusToken token = super.beforeInvocation(ri);

        	try {
        		return invocation.invoke();
        	} finally {
        		super.afterInvocation(token, null);
        	}
        }
	}

	protected abstract RoutingInvocation buildRoutingInvocation(ActionInvocation invocation, ActionRouting routing);

	public RoutingInvocationDefinitionSource getObjectDefinitionSource() {
		return objectDefinitionSource;
	}

	public void setObjectDefinitionSource(RoutingInvocationDefinitionSource objectDefinitionSource) {
		this.objectDefinitionSource = objectDefinitionSource;
	}

	public boolean isObserveOncePerRequest() {
		return observeOncePerRequest;
	}

	public void setObserveOncePerRequest(boolean observeOncePerRequest) {
		this.observeOncePerRequest = observeOncePerRequest;
	}

}