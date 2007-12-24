/**
 * Created on 2007-7-12
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.result;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author Sunteya
 *
 */
public class ReferRedirectResult extends RedirectResult {

	private static final long serialVersionUID = 3370069309119737831L;
	private Map<String, String> referMapping;


	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		String referUrl = findReferUrl(invocation);
		if(StringUtils.isNotEmpty(referUrl)) {
			setLocation(referUrl);
		}

		super.execute(invocation);
	}

	protected String findReferUrl(ActionInvocation invocation) {
		String referName = referMapping.get(invocation.getResultCode());
		if(referName == null) {
			return null;
		}

		String stackAnswer = findByStack(invocation, referName);
		if(stackAnswer != null) {
			return stackAnswer;
		}

		return findByRouting(invocation, referName);
	}

	protected String findByRouting(ActionInvocation invocation, String referName) {
		ActionContext ctx = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
        return request.getParameter(referName);
	}

	protected String findByStack(ActionInvocation invocation, String referName) {
		ValueStack stack = invocation.getStack();
		String answer = stack.findString(referName);
		return answer;
	}

	public void setReferMapping(Map<String, String> referMapping) {
		this.referMapping = referMapping;
	}
}
