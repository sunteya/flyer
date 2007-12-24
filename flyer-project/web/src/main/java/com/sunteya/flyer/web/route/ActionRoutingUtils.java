/**
 * Created on 2007-7-5
 * Created by Sunteya
 */
package com.sunteya.flyer.web.route;

import javax.servlet.ServletRequest;

/**
 * @author Sunteya
 *
 */

public abstract class ActionRoutingUtils {

	public static final String ACTION_ROUTING_CONTEXT_ATTRIBUTE = "ActionRouting" + ".CONTEXT";

	@Deprecated
	public static ActionRouting getActionRouting(ServletRequest request) throws IllegalStateException {
		return ActionRoutingHolder.peekLastRouting();
	}

	@Deprecated
	public static void setActionRoutingOncePerRequest(ActionRouting routing, ServletRequest request) {
		if(getActionRouting(request) == null) {
			request.setAttribute(ACTION_ROUTING_CONTEXT_ATTRIBUTE, routing);
		}
	}

	public static int getIntParameter(ActionRouting routing, String name, int defaultValue) {
		try {
			return Integer.valueOf(routing.getParams().get(name)[0]);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
