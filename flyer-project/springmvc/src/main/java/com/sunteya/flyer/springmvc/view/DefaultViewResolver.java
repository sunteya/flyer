/**
 * Created on 2007-3-1
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.view;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * @author Sunteya
 *
 */
public class DefaultViewResolver implements ViewResolver, Ordered {

	private Class<? extends View> viewClass;
	private int order = Integer.MAX_VALUE;

	public void setOrder(int order) {
		this.order = order;
	}

	public Class<? extends View> getViewClass() {
		return viewClass;
	}

	public void setViewClass(Class<? extends View> viewClass) {
		this.viewClass = viewClass;
	}

	public int getOrder() {
		return order;
	}

	public View resolveViewName(String viewName, Locale locale)	throws Exception {
		if(StringUtils.isBlank(viewName)) {
			return null;
		}

		return getViewClass().newInstance();
	}
}
