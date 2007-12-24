/**
 * Created on 2007-1-26
 * Created by Lewisou
 */
package com.sunteya.flyer.web.support;

import com.sunteya.flyer.support.RequestPage;
import com.sunteya.flyer.web.route.ActionRouting;
import com.sunteya.flyer.web.route.ActionRoutingUtils;

/**
 * @author Lewisou
 *
 */
public class GetMethodRequestPage implements RequestPage {

	private static final long serialVersionUID = 2616662858255180137L;

	private ActionRouting actionRouting;
	private String pageIndexKey;
	private String pageSizeKey;
	private boolean allowCustomPageSize;
	private int defaultPageSize;

	public GetMethodRequestPage() {
	}

	public GetMethodRequestPage(ActionRouting routing, int defaultPageSize) {
		this(routing, defaultPageSize, false);
	}

	public GetMethodRequestPage(ActionRouting routing, int defaultPageSize, boolean allowCustomPageSize) {
		setActionRouting(routing);
		setAllowCustomPageSize(allowCustomPageSize);
		setDefaultPageSize(defaultPageSize);
	}

	@SuppressWarnings("unchecked")
	public String getPageUrl(int pageIndex) {
		ActionRouting routing = (ActionRouting) getActionRouting().clone();
		routing.setSingleParam(getPageIndexKey(), String.valueOf(pageIndex));

		if (isAllowCustomPageSize()) {
			routing.setSingleParam(getPageSizeKey(), String.valueOf(getRequestPageSize()));
		} else {
			routing.getParams().remove(getRequestPageSize());
		}

		return routing.toUrl();
	}

	public int getRequestPageIndex() {
		return ActionRoutingUtils.getIntParameter(getActionRouting(), getPageIndexKey(), 1);
	}

	public void setDefaultPageSize(int pageSize) {
		this.defaultPageSize = pageSize;
	}

	public int getRequestPageSize() {
		if (isAllowCustomPageSize()) {
			return ActionRoutingUtils.getIntParameter(getActionRouting(), getPageSizeKey(), getDefaultPageSize());
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

	public ActionRouting getActionRouting() {
		return actionRouting;
	}

	public void setActionRouting(ActionRouting routing) {
		this.actionRouting = routing;
	}

	public String getPageIndexKey() {
		return pageIndexKey;
	}

	public void setPageIndexKey(String pageIndexKey) {
		this.pageIndexKey = pageIndexKey;
	}

	public String getPageSizeKey() {
		return pageSizeKey;
	}

	public void setPageSizeKey(String pageSizeKey) {
		this.pageSizeKey = pageSizeKey;
	}
}
