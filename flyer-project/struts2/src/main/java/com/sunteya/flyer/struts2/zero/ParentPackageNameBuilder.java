/**
 * Created on 2007-9-20
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.zero;

import com.opensymphony.xwork2.Action;

/**
 * @author Sunteya
 *
 */
public interface ParentPackageNameBuilder {

	String build(Class<? extends Action> actionClass);

}
