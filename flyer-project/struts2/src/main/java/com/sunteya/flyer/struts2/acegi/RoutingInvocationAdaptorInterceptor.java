/**
 * Created on 2007-8-1
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.acegi;

import com.opensymphony.xwork2.ActionInvocation;
import com.sunteya.flyer.acegi.web.RoutingInvocation;
import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public class RoutingInvocationAdaptorInterceptor extends AbstractRoutingInvocationInterceptor  {

	private static final long serialVersionUID = -7977574792274673755L;

	@Override
	protected RoutingInvocation buildRoutingInvocation(ActionInvocation invocation, ActionRouting routing) {
		return new RoutingInvocationAdaptor(invocation.getAction(), routing);
	}
}
