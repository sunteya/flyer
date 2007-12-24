/**
 * Created on 2007-6-12
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.result;

import static com.sunteya.flyer.web.route.UrlBuilderHelper.*;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.apache.struts2.dispatcher.mapper.ActionMapper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.util.ValueStack;
import com.sunteya.flyer.struts2.config.ActionMapperHelper;
import com.sunteya.flyer.web.route.ActionRouting;
import com.sunteya.flyer.web.route.ActionRoutingHolder;
import com.sunteya.flyer.web.util.RequestUtils;

/**
 * @author Sunteya
 *
 */
public class RedirectResult implements Result {

	private static final long serialVersionUID = -7261944020676778495L;

	public static final String DEFAULT_PARAM = StrutsResultSupport.DEFAULT_PARAM;
	protected ActionMapper actionMapper;
	private String location;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Inject
    public void setActionMapper(ActionMapper mapper) {
        this.actionMapper = mapper;
    }

	public void execute(ActionInvocation invocation) throws Exception {
        ActionContext ctx = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) ctx.get(StrutsStatics.HTTP_RESPONSE);
        ActionRouting routing = ActionRoutingHolder.peekLastRouting();

		String lastFinalLocation = build(invocation.getStack(), routing);
		String url = RequestUtils.appendContextPathIfNeed(request, lastFinalLocation);
		String finalUrl = response.encodeRedirectURL(url);
		response.sendRedirect(finalUrl);
	}

	private String build(ValueStack stack, ActionRouting routing) {
		if(isExpression(location)) {
			Map<String, Object> params = parseExpression(location, stack);
			ActionRouting target = buildActionMapping(routing, params);
			return ActionMapperHelper.getUriFromActionMapping(actionMapper, target);
		} else {
			return conditionalParse(location, stack);
		}
	}

	protected boolean isExpression(String source) {
		return StringUtils.contains(source, ":") && !StringUtils.contains(source, "://");
	}


	protected Map<String, Object> parseExpression(String source, ValueStack stack) {
		Map<String, Object> answer = new TreeMap<String, Object>();
		String[] entries = StringUtils.split(source, ",");
		for (String entry : entries) {
			String key = StringUtils.substringBefore(entry, ":").trim();
			String expression = StringUtils.substringAfter(entry, ":").trim();
			Object value = expression;

			Pattern pattern = Pattern.compile("^#\\{(.*)\\}$");
			Matcher matcher = pattern.matcher(expression.trim());
			if(matcher.find()) {
				String expr = matcher.group(1);
				value = stack.findValue(expr);
			}

			answer.put(key, value);
		}


		return answer;
	}

	protected String conditionalParse(String param, ValueStack stack) {
		return TextParseUtil.translateVariables('#', param, stack);
    }
}
