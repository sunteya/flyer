/**
 * Created on 2007-9-21
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.zero.impl;

import org.apache.struts2.config.ParentPackage;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.inject.Inject;
import com.sunteya.flyer.struts2.zero.ParentPackageNameBuilder;

/**
 * @author Sunteya
 *
 */
public class DefaultParentPackageNameBuilder implements ParentPackageNameBuilder {

	private String defaultParentPackage;

	public String build(Class<? extends Action> actionClass) {
		ParentPackage parent = actionClass.getAnnotation(ParentPackage.class);
		return parent == null ? getDefaultParentPackage() : parent.value();
	}

	public String getDefaultParentPackage() {
		return defaultParentPackage;
	}

	@Inject(value="flyer.default.parent.package")
	public void setDefaultParentPackage(String defaultParentPackage) {
		this.defaultParentPackage = defaultParentPackage;
	}
}
