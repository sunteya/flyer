/**
 * Created on 2007-9-21
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.zero.impl;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.inject.Inject;
import com.sunteya.flyer.struts2.zero.PackageNameBuilder;
import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public class DefaultPackageNameBuilder implements PackageNameBuilder {

	private String rootPackage = "ROOT";

	public String build(ActionRouting routing) {

		String pkgAndCtl = routing.getPackageAndController();
		if(StringUtils.isBlank(pkgAndCtl) || "/".equals(pkgAndCtl)) {
			return rootPackage;
		} else {
			return pkgAndCtl;
		}
	}

	public String getRootPackage() {
		return rootPackage;
	}

	@Inject(value="flyer.root.package", required=false)
	public void setRootPackage(String rootPackageName) {
		this.rootPackage = rootPackageName;
	}
}
