/**
 * Created on 2007-8-20
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
public class SimpleRoutingInvocationInterceptor extends AbstractRoutingInvocationInterceptor {

	private static final long serialVersionUID = 2869761298547433081L;

	@Override
	protected RoutingInvocation buildRoutingInvocation(ActionInvocation invocation, ActionRouting routing) {
		SimpleRoutingInvocation answer = new SimpleRoutingInvocation();
		answer.setRouting(routing);
		return answer;
	}

}
