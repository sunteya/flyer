/**
 * Created on 2007-7-31
 * Created by Sunteya
 */
package com.sunteya.flyer.acegi.web;


import com.sunteya.flyer.acegi.ConditionInvocation;
import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public interface RoutingInvocation extends ConditionInvocation {
	public ActionRouting getRouting();
}
