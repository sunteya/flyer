/**
 * Created on 2007-11-30
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.action;



/**
 * @author Sunteya
 *
 */
public class GetLifecycle implements ActionLifecycle {

	public void bindAndValidate() throws Exception {
	}

	public String execute() throws Exception {
		return INPUT;
	}

	public void initBinderAndValidator() throws Exception {
	}

	public void prepare() throws Exception {
	}
}
