/**
 * Created on 2007-6-8
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.sitemesh;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.Dispatcher;

import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.sunteya.flyer.struts2.config.Struts2ConfigUtils;
import com.sunteya.flyer.web.sitemesh.AbstractRepeatDecoratorPageFilter;

/**
 * @author Sunteya
 *
 */
public class Struts2RepeatPageFilter extends AbstractRepeatDecoratorPageFilter {

	@Override
	protected void doRander(Page page, Decorator decorator, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Result result = findResult(decorator);

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("title", page.getTitle());

		StringWriter body = new StringWriter();
		page.writeBody(body);
		model.put("body", body.toString());

		if(page instanceof HTMLPage) {
			HTMLPage htmlPage = (HTMLPage) page;
			StringWriter head = new StringWriter();
			htmlPage.writeHead(head);
			model.put("head", head.toString());
		}

		model.put("page", page);
		ServletActionContext.getValueStack(ServletActionContext.getRequest()).push(model);
		ServletActionContext.setRequest(request);
		ServletActionContext.setResponse(response);

		result.execute(ActionContext.getContext().getActionInvocation());
	}

	private Result findResult(Decorator decorator) throws Exception {
		ObjectFactory objectFactory = ObjectFactory.getObjectFactory();

		ResultConfig resultConfig = getResultConfig(decorator);
		Result result = objectFactory.buildResult(resultConfig, ActionContext.getContext().getContextMap());
		return result;
	}

	private ResultConfig getResultConfig(Decorator decorator) {
		return Struts2ConfigUtils.getReturnConfig(Action.SUCCESS, decorator.getPage(), getCurrentActionPackageConfig());
	}

	protected PackageConfig getCurrentActionPackageConfig() {
		String packageName = ActionContext.getContext().getActionInvocation().getProxy().getConfig().getPackageName();
		return Dispatcher.getInstance().getConfigurationManager().getConfiguration().getPackageConfig(packageName);
	}

}
