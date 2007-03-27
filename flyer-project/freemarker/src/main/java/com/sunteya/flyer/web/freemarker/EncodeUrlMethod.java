/**
 * Created on 2006-11-20
 * Created by Sunteya
 */
package com.sunteya.flyer.web.freemarker;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.sunteya.flyer.web.holder.HttpServletHolder;
import com.sunteya.flyer.web.util.RequestUtils;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * @author Sunteya
 *
 */
public class EncodeUrlMethod implements TemplateMethodModel {

	@SuppressWarnings("unchecked")
	public Object exec(List arguments) throws TemplateModelException {

		String baseUrl = buildBaseUrl(arguments.get(0).toString());

		Iterator iter = arguments.iterator();
		iter.next(); // skip base link;

		StringBuilder targetUrl = new StringBuilder();
		targetUrl.append(baseUrl);
		String queryString = StringUtils.join(iter, "&");
		if (StringUtils.isNotBlank(queryString)) {
			if (baseUrl.indexOf("?") >= 0) {
				targetUrl.append("&");
			} else {
				targetUrl.append("?");
			}

			targetUrl.append(queryString);
		}

		return HttpServletHolder.getResponse().encodeURL(targetUrl.toString());
	}

	private String buildBaseUrl(String path) {
		StringBuilder answer = new StringBuilder();
		HttpServletRequest request = HttpServletHolder.getRequest();

		String contextPath = request.getContextPath();
		if (!"/".equals(contextPath)) {
			answer.append(contextPath);
		}

		answer.append(RequestUtils.absolutePath(request, path));
		return answer.toString();
	}
}
