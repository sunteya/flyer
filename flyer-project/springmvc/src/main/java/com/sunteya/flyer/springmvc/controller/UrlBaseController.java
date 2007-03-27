/**
 * Created on 2007-3-1
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.util.UrlPathHelper;

/**
 * @author Sunteya
 *
 */
public class UrlBaseController extends AbstractController {

	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	public UrlBaseController() {
		urlPathHelper.setAlwaysUseFullPath(true);
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String viewName = urlPathHelper.getLookupPathForRequest(request);
		return new ModelAndView(viewName);
	}

}
