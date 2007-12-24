/**
 * Created on 2008-1-29
 * Created by Sunteya
 */
package com.sunteya.flyer.web.route;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author Sunteya
 *
 */
public class ActionRoutingHolder {
	private static ThreadLocal<Deque<ActionRouting>> routingDeques = new InheritableThreadLocal<Deque<ActionRouting>>();
	
	public static Deque<ActionRouting> getRoutingDeque() {
		return routingDeques.get();
	}
	
	public static void setRoutingDeque(Deque<ActionRouting> routingList) {
		routingDeques.set(routingList);
	}
	
	public static void clearRoutingList() {
		routingDeques.remove();
	}
	
	public static ActionRouting peekFirstRouting() {
		return getRoutingDeque().peekFirst();
	}
	
	public static ActionRouting peekLastRouting() {
		return getRoutingDeque().peekLast();
	}
	
	public static void addLastRouting(ActionRouting routing) {
		Deque<ActionRouting> routingList = getRoutingDeque();
		if(routingList == null) {
			routingList = new LinkedList<ActionRouting>();
		}
		routingList.addLast(routing);
		setRoutingDeque(routingList);
	}
}
