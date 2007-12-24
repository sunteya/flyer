/**
 * Created on 2007-12-7
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.result;

import javax.servlet.RequestDispatcher;

/**
 * @author Sunteya
 *
 */
public class DefaultServletResult extends AbstractResult {

	private static final long serialVersionUID = -7261944020676778495L;

	@Override
	protected void execute() throws Exception {
		RequestDispatcher dispatcher = getServletContext().getNamedDispatcher("default");
		dispatcher.forward(getRequest(), getResponse());
	}
}
