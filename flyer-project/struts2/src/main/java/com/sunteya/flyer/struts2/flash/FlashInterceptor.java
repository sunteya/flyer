/**
 * Created on 2007-6-21
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.flash;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author Sunteya
 *
 */
public class FlashInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 7696649741881314704L;

	public static final String FLASH_SESSION_KEY = "Flash.session";
	public static final String FLASH_MODEL = "com.opensymphony.xwork2.ActionContext.flash";

	@Override
	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		readyPreviousFlashForView(session, invocation.getInvocationContext());
		prepareForNextRequestIfNeed(invocation.getAction(), session);

		return invocation.invoke();
	}

	protected void prepareForNextRequestIfNeed(Object action, Map<String, Object> session) {
		Map<String, Object> flash = new HashMap<String, Object>();
		hookFlash(session, flash);

		if(action instanceof FlashAware) {
			((FlashAware) action).setFlash(flash);
		}
	}

	protected void hookFlash(Map<String, Object> session, Map<String, Object> flash) {
		session.put(FLASH_SESSION_KEY, flash);
	}

	@SuppressWarnings("unchecked")
	protected void readyPreviousFlashForView(Map<String, Object> session, ActionContext actionContext) {
		Map<String, Object> flashModel =  unhookFlash(session);
		actionContext.put(FLASH_MODEL, flashModel);
	}

	@SuppressWarnings("unchecked")
	protected Map<String, Object> unhookFlash(Map<String, Object> session) {
		return (Map<String, Object>) ObjectUtils.defaultIfNull(
					session.remove(FLASH_SESSION_KEY), new HashMap<String, Object>());
	}
}
