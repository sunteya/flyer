/**
 * Created on 2007-2-27
 * Created by Sunteya
 */
package com.sunteya.flyer.web.sitemesh.springmvc;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.OrderComparator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.Page;
import com.sunteya.flyer.web.sitemesh.AbstractRepeatDecoratorPageFilter;

/**
 * @author Sunteya
 *
 */
public class SpringRepeatPageFilter extends AbstractRepeatDecoratorPageFilter {

	@Override
	protected void doRander(Page page, Decorator decorator, HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		View view = findView(decorator, request);

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
		model.put("base", request.getContextPath());

		view.render(model, request, response);
	}

	private View findView(Decorator decorator, HttpServletRequest request) throws Exception {
		String viewName = decorator.getPage();

		List<ViewResolver> resolvers = getViewResolvers(request);
		for(ViewResolver resolver : resolvers) {
			View view = resolver.resolveViewName(viewName, request.getLocale());
			if(view != null) {
				return view;
			}
		}

		throw new RuntimeException("view not found, viewName=" + viewName);
	}

	@SuppressWarnings("unchecked")
	private List<ViewResolver> getViewResolvers(HttpServletRequest request) {
		WebApplicationContext ctx = RequestContextUtils.getWebApplicationContext(request);
		Map<String, ViewResolver> resolverMap = ctx.getBeansOfType(ViewResolver.class);

		List<ViewResolver> answer = new ArrayList<ViewResolver>();
		for (ViewResolver resolver : resolverMap.values()) {
			answer.add(resolver);
		}

		Collections.sort(answer, new OrderComparator());
		return answer;
	}
}
