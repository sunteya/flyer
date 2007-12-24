/**
 * Created on 2007-9-20
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.zero;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;

/**
 * @author Sunteya
 *
 */
public interface ResultBuilder {
	Map<String, ResultConfig> build(Class<? extends Action> actionClass, PackageConfig packageConfig);
}
