/**
 * Created on 2007-1-26
 * Created by Lewisou
 */
package com.sunteya.flyer.web.support;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lewisou
 *
 */
public abstract class RequestPageFacorty {

	public static RequestPage createRequestPage(HttpServletRequest request,
			boolean allowCustomPageSize, int defaultPageSize) {
		RequestPage requestPage = new RequestPage();
		requestPage.setRequest(request);
		requestPage.setAllowCustomPageSize(allowCustomPageSize);
		requestPage.setDefaultPageSize(defaultPageSize);
		return requestPage;
	}

}
