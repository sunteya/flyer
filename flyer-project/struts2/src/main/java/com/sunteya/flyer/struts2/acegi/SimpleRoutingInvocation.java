/**
 * Created on 2007-8-7
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.acegi;

import java.util.ArrayList;
import java.util.List;

import com.sunteya.flyer.acegi.web.RoutingInvocation;
import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public class SimpleRoutingInvocation implements RoutingInvocation {

	private ActionRouting routing;
	private List<Object> domainModels = new ArrayList<Object>();

	public ActionRouting getRouting() {
		return routing;
	}

	public List<Object> getDomainModels() {
		return domainModels;
	}

	public void setRouting(ActionRouting routing) {
		this.routing = routing;
	}

	public void setDomainModels(List<Object> domainModels) {
		this.domainModels = domainModels;
	}
}
