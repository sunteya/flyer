/**
 * Created on 2007-12-7
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.result;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionProxyFactory;
import com.opensymphony.xwork2.inject.Inject;

/**
 * @author Sunteya
 *
 */
public class ChainResult extends AbstarctChainResult {

	private static final long serialVersionUID = -7261944020676778495L;

	private ActionProxyFactory actionProxyFactory;

	@Override
	protected void execute() throws Exception {
		final String path = build(getActionInvocation().getStack(), getRoutingQeque().peekLast());
		ActionMapping mapping = actionMapper.getMapping(new HttpServletRequestWrapper(getRequest()) {
			@Override
			public String getServletPath() {
				return path;
			}

			@Override
			public String getRequestURI() {
				return getContextPath() + getServletPath();
			}

			@Override
			public StringBuffer getRequestURL() {
				return new StringBuffer(getRequestURI());
			}
		}, Dispatcher.getInstance().getConfigurationManager());

        Map<String, Object> extraContext = new HashMap<String, Object>();
        extraContext.put(ActionContext.VALUE_STACK, ActionContext.getContext().getValueStack());
        extraContext.put(ActionContext.PARAMETERS, ActionContext.getContext().getParameters());

        ActionProxy proxy = actionProxyFactory.createActionProxy(mapping.getNamespace(), mapping.getName(), extraContext);
        proxy.execute();
	}
	
	protected void doExecute(HttpServletRequest request, HttpServletResponse response, ActionInvocation invocation) throws Exception {

	}

    @Inject
    public void setActionProxyFactory(ActionProxyFactory actionProxyFactory) {
        this.actionProxyFactory = actionProxyFactory;
    }
}
