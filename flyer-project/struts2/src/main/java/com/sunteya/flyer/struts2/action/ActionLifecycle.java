/**
 * Created on 2007-11-30
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.action;

import com.opensymphony.xwork2.Action;

/**
 * @author Sunteya
 *
 */
public interface ActionLifecycle extends Action {

	void prepare() throws Exception;

	void initBinderAndValidator() throws Exception;

	void bindAndValidate() throws Exception;

	String execute() throws Exception;
}
