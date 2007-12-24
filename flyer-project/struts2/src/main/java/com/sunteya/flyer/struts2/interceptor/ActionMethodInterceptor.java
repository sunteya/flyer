/**
 * Created on 2007-6-15
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sunteya.commons.util.StringUtility;

/**
 * @author Sunteya
 *
 */
public class ActionMethodInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 8315099048074694730L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		ActionMapping mapping = ServletActionContext.getActionMapping();
		String actionName = StringUtils.substringBefore(mapping.getName().toLowerCase(), ".");
		String method = StringUtility.camel(actionName, "-");

		try {
			Class<?> actionClass = invocation.getAction().getClass();
			actionClass.getMethod(actionName);
			invocation.getProxy().setMethod(method);
		} catch (Exception skip) { }

		return invocation.invoke();
	}

}
