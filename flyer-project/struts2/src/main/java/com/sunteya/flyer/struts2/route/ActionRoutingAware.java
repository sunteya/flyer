/**
 * Created on 2007-6-21
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.route;

import java.util.Deque;

import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public interface ActionRoutingAware {
	void setRoutingDeque(Deque<ActionRouting> routingDeque);
}
