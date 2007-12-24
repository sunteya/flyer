/**
 * Created on 2007-12-3
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.interceptor;

import com.sunteya.flyer.struts2.action.ActionLifecycle;

/**
 * @author Sunteya
 *
 */
public class BindAndValidateInterceptor extends LifecycleInterceptor {

	private static final long serialVersionUID = -5462972648117315969L;

	@Override
	protected void doIntercept(ActionLifecycle action) throws Exception {
		action.bindAndValidate();
	}
}
