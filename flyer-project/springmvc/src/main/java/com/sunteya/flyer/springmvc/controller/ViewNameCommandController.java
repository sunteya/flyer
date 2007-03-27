/**
 * Created on 2006-12-30
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.controller;


/**
 * @author Sunteya
 *
 * @param <Cmd>
 */
public abstract class ViewNameCommandController<Cmd> extends
		AbstractGenericCommandController<Cmd> {

	private String viewName;

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
}