/**
 * Created on 2007-7-9
 * Created by Sunteya
 */
package com.sunteya.flyer.web.ui;

import static com.sunteya.flyer.web.util.RequestUtils.*;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunteya.flyer.web.route.ActionRouting;
import com.sunteya.flyer.web.route.ActionRoutingHolder;

/**
 * @author Sunteya
 *
 */
public abstract class AbstractUrlHelper {

	public String computePublicPath(String source, String base, String ext) {
		return  urlFor('/' + base + '/' + source + "." + ext);
	}

	public String urlFor(Object option) {
		HttpServletResponse response = getResponse();
		return response.encodeURL(appendContextPathIfNeed(getRequest(), buildUrl(option)));
	}

	protected abstract HttpServletResponse getResponse();

	protected abstract HttpServletRequest getRequest();

	@SuppressWarnings("unchecked")
	public String buildUrl(Object option) {
		if(option instanceof Map) {
			return buildUrlByMap(new HashMap((Map) option));
		} else {
			return option.toString();
		}
	}

	protected abstract String buildUrlByMap(Map<String, Object> hashMap);

	protected ActionRouting getFirstRoutingWithNoParam() {
		ActionRouting routing = (ActionRouting) ActionRoutingHolder.peekFirstRouting().clone();
		routing.getParams().clear();
		return routing;
	}
}