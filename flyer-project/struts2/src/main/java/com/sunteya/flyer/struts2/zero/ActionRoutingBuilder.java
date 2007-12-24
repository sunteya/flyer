/**
 * Created on 2007-9-20
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.zero;

import java.util.List;

import com.opensymphony.xwork2.Action;
import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public interface ActionRoutingBuilder {
	List<ActionRouting> build(Class<? extends Action> actionClass, String basePackage);
}
