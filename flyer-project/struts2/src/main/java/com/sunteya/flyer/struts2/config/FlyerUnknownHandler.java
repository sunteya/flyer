/**
 * Created on 2007-6-22
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.config;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.UnknownHandler;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.inject.Inject;

/**
 * @author Sunteya
 *
 */
public class FlyerUnknownHandler implements UnknownHandler {

	private ResultManager resultManager;


	public ActionConfig handleUnknownAction(String namespace, String actionName) throws XWorkException {
		return null;
	}

	public Result handleUnknownResult(ActionContext actionContext, String actionName, ActionConfig actionConfig, String resultCode)
			throws XWorkException {
		return resultManager.findResult(actionContext, actionName, actionConfig, resultCode);
	}

	@Inject
	public void setResultManager(ResultManager resultManager) {
		this.resultManager = resultManager;
	}
}
