/**
 * Created on 2007-1-26
 * Created by Lewisou
 */
package com.sunteya.flyer.web.support;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestUtils;

import com.sunteya.flyer.util.UrlUtils;

/**
 * @author Lewisou
 * 
 */
public class RequestPage {

	final public static String PAGE_KEY = "page";
	final public static String SIZE_KEY = "size";

	private HttpServletRequest request;
	private boolean allowCustomPageSize;
	private int defaultPageSize;

	public RequestPage() {
	}

	public RequestPage(HttpServletRequest request, int defaultPageSize) {
		this(request, defaultPageSize, false);
	}

	public RequestPage(HttpServletRequest request, int defaultPageSize,
			boolean allowCustomPageSize) {
		super();
		this.request = request;
		this.allowCustomPageSize = allowCustomPageSize;
		this.defaultPageSize = defaultPageSize;
	}

	@SuppressWarnings("unchecked")
	public String getPageUrl(int pageIndex) {
		Map<String, String[]> params = new HashMap<String, String[]>(
				getRequest().getParameterMap());

		params.put(PAGE_KEY, new String[] { String.valueOf(pageIndex) });

		if (isAllowCustomPageSize()) {
			params.put(SIZE_KEY, new String[] { String
					.valueOf(getRequestPageSize()) });
		}

		return UrlUtils.buildUrl(getRequest().getRequestURI(), params,
				getRequest().getCharacterEncoding());
	}

	public int getRequestPageIndex() {
		return ServletRequestUtils.getIntParameter(getRequest(), PAGE_KEY, 1);
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setDefaultPageSize(int pageSize) {
		this.defaultPageSize = pageSize;
	}

	public int getRequestPageSize() {
		if (isAllowCustomPageSize()) {
			return ServletRequestUtils.getIntParameter(getRequest(), SIZE_KEY,
					getDefaultPageSize());
		}

		return getDefaultPageSize();
	}

	public int getDefaultPageSize() {
		return defaultPageSize;
	}

	public void setAllowCustomPageSize(boolean allowCustomPageSize) {
		this.allowCustomPageSize = allowCustomPageSize;
	}

	public boolean isAllowCustomPageSize() {
		return allowCustomPageSize;
	}
}
