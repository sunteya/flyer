/**
 * Created on 2007-11-26
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.action;

import java.util.Deque;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.sunteya.flyer.struts2.flash.FlashAware;
import com.sunteya.flyer.struts2.route.ActionRoutingAware;
import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public abstract class ActionSupport implements Action, ActionLifecycle, ActionRoutingAware, ServletRequestAware, SessionAware, ParameterAware, FlashAware {

	protected HttpServletRequest request;
	protected Map<String, Object> session;
	protected Map<String, Object> parameters;
	protected Map<String, Object> flash;
	protected Deque<ActionRouting> routingDeque;

	// =====================================================
	// Methods
	// -----------------------------------------------------
	public void prepare() throws Exception {
	}

	public void initBinderAndValidator() {
	}

	public void bindAndValidate() throws Exception {
	}

	public String execute() throws Exception {
		return SUCCESS;
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@SuppressWarnings("unchecked")
	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	public void setFlash(Map<String, Object> flash) {
		this.flash = flash;
	}

	public void setRoutingDeque(Deque<ActionRouting> routingDeque) {
		this.routingDeque = routingDeque;
	}
	
	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}
}
