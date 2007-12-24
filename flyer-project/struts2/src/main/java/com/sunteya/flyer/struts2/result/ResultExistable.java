/**
 * Created on 2008-1-11
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.result;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author Sunteya
 *
 */
public interface ResultExistable {
	boolean exists(ActionContext actionContext);
}
