/**
 * Created on 2007-3-1
 * Created by Sunteya
 */
package com.sunteya.flyer.web.holder;

import com.sunteya.flyer.util.UrlUtils;


/**
 * @author Sunteya
 *
 */
public abstract class PageUrlHolder {
	private static ThreadLocal<String> pageUrlThreadLocal = new InheritableThreadLocal<String>();

	public static String get() {
		return pageUrlThreadLocal.get();
	}

	public static void set(String pageUrl) {
		pageUrlThreadLocal.set(pageUrl);
	}

	public static void clear() {
		pageUrlThreadLocal.remove();
	}

	public static String getRootUrl() {
		return UrlUtils.getRootUrl(getUrl());
	}

	public static String getUrl() {
		return get();
	}
}
