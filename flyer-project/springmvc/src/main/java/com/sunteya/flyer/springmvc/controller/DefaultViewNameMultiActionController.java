/**
 * Created on 2006-12-30
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * @author Sunteya
 *
 */
public class DefaultViewNameMultiActionController extends MultiActionController {

	private String defaultViewName;

	public void setDefaultViewName(String defaultViewName) {
		this.defaultViewName = defaultViewName;
	}

	public String getDefaultViewName() {
		return defaultViewName;
	}

	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = super.handleRequestInternal(request, response);

		if (getDefaultViewName() == null) {
			return mv;
		}

		if (mv == null) {
			mv = new ModelAndView(getDefaultViewName());
		}

		if (mv.getViewName() == null && mv.getView() == null) {
			mv.setViewName(getDefaultViewName());
		}

		return mv;
	}
}