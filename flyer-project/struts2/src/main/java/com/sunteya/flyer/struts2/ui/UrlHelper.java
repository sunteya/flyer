/**
 * Created on 2007-6-8
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.ui;

import static com.sunteya.flyer.web.route.UrlBuilderHelper.*;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.mapper.ActionMapper;

import com.opensymphony.xwork2.inject.Container;
import com.sunteya.flyer.struts2.config.ActionMapperHelper;
import com.sunteya.flyer.web.route.ActionRouting;
import com.sunteya.flyer.web.ui.AbstractUrlHelper;

/**
 * @author Sunteya
 *
 */
public class UrlHelper extends AbstractUrlHelper {

	@Override
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	@Override
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	@Override
	protected String buildUrlByMap(Map<String, Object> params) {
		ActionRouting target = buildActionMapping(getFirstRoutingWithNoParam(), params);
		return ActionMapperHelper.getUriFromActionMapping(getActionMapper(), target);
	}

	private ActionMapper getActionMapper() {
		Container container = Dispatcher.getInstance().getConfigurationManager().getConfiguration().getContainer();
		ActionMapper actionMapper = container.getInstance(ActionMapper.class);
		return actionMapper;
	}
}
