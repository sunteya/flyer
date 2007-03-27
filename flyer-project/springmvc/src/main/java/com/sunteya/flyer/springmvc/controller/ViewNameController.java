/**
 * Created on 2006-12-30
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.controller;

import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @author Sunteya
 *
 */
public abstract class ViewNameController extends AbstractController {

	private String viewName;

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
}