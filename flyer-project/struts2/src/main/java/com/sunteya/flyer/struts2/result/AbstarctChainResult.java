/**
 * Created on 2007-12-7
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
import org.apache.struts2.dispatcher.mapper.ActionMapper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.util.ValueStack;
import com.sunteya.flyer.struts2.config.ActionMapperHelper;
import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public abstract class AbstarctChainResult extends AbstractResult {

	protected ActionMapper actionMapper;

	@Inject
	public void setActionMapper(ActionMapper mapper) {
	    this.actionMapper = mapper;
	}

	protected String build(ValueStack stack, ActionRouting routing) {
		if(isRoutingStyle(getLocation())) {
			Map<String, Object> params = parseRoutingParams(getLocation(), stack);
			ActionRouting target = buildActionMapping(routing, params);
			return ActionMapperHelper.getUriFromActionMapping(actionMapper, target);
		} else {
			return conditionalParse(getLocation(), stack);
		}
	}

	protected boolean isRoutingStyle(String source) {
		return StringUtils.contains(source, ":") && !StringUtils.contains(source, "://");
	}

	protected Map<String, Object> parseRoutingParams(String source, ValueStack stack) {
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

	public void execute(ActionInvocation invocation) throws Exception {
	    ActionContext ctx = invocation.getInvocationContext();
	    HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
	    HttpServletResponse response = (HttpServletResponse) ctx.get(StrutsStatics.HTTP_RESPONSE);

		doExecute(request, response, invocation);
	}

	protected abstract void doExecute(HttpServletRequest request, HttpServletResponse response, ActionInvocation invocation) throws Exception;
}