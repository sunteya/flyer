/**
 * Created on 2007-6-20
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.interceptor;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author Sunteya
 *
 */
public class ParametersTrimmerInterceptor extends AbstractInterceptor {


	private static final long serialVersionUID = 532828520951411650L;

	@Override
	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
		for(Map.Entry<String, Object> entry : parameters.entrySet()) {
			trimEntry(entry);
		}

		return invocation.invoke();
	}

	protected void trimEntry(Entry<String, Object> entry) {
		if(!(entry.getValue() instanceof String[])) {
			return;
		}
		String[] entryValues = (String[]) entry.getValue();

		for (int i = 0; i < entryValues.length; i++) {
			entryValues[i] = StringUtils.trim(entryValues[i]);
		}

		boolean emptyAll = true;
		for (String value : entryValues) {
			if(StringUtils.isNotEmpty(value)) {
				emptyAll = false;
			}
		}

		if(emptyAll) {
			entry.setValue(null);
		}
	}
}