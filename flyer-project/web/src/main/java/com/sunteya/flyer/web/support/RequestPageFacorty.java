/**
 * Created on 2007-1-26
 * Created by Lewisou
 */
package com.sunteya.flyer.web.support;

import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public abstract class RequestPageFacorty {

	public static GetMethodRequestPage createGetMethodRequestPage(ActionRouting routing, int defaultPageSize)  {
		return createGetMethodRequestPage(routing, defaultPageSize, false);
	}

	public static GetMethodRequestPage createGetMethodRequestPage(ActionRouting routing,
			int defaultPageSize, boolean customSize) {
		GetMethodRequestPage requestPage = createGetMethodRequestPage();
		requestPage.setActionRouting(routing);
		requestPage.setAllowCustomPageSize(customSize);
		requestPage.setDefaultPageSize(defaultPageSize);
		return requestPage;
	}

	private static GetMethodRequestPage createGetMethodRequestPage() {
		GetMethodRequestPage requestPage = new GetMethodRequestPage();
		requestPage.setPageIndexKey("page");
		requestPage.setPageSizeKey("size");
		return requestPage;
	}
}
