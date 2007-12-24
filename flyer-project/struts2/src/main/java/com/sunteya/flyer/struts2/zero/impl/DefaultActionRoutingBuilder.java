/**
 * Created on 2007-9-20
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.zero.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.inject.Inject;
import com.sunteya.commons.util.StringUtility;
import com.sunteya.flyer.struts2.zero.ActionRoutingBuilder;
import com.sunteya.flyer.struts2.zero.annotation.Routing;
import com.sunteya.flyer.struts2.zero.annotation.Routings;
import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public class DefaultActionRoutingBuilder implements ActionRoutingBuilder {

	private String defaultExtension = "html";
	private String separator;
	private String actionSuffix = "Action";

	public List<ActionRouting> build(Class<? extends Action> actionClass, String basePackage) {
		List<ActionRouting> answer = new ArrayList<ActionRouting>();

		ActionRouting routing = buildPackageBaseName(actionClass, basePackage);
		answer.addAll(createByAnnotation(actionClass, routing));
		if(answer.isEmpty()) {
			answer.add(routing);
		}

		return answer;
	}

	private List<ActionRouting> createByAnnotation(Class<? extends Action> actionClass, ActionRouting packageBaseRouting) {
		List<ActionRouting> answer = new ArrayList<ActionRouting>();

		for (Routing annotation : getRoutings(actionClass)) {
			ActionRouting routing = new ActionRouting();
			if(annotation.combine()) {
				routing = (ActionRouting) packageBaseRouting.clone();
			}

			if(StringUtils.isNotBlank(annotation.controller())) {
				routing.parsePackageAndController(annotation.controller());
			}
			if(StringUtils.isNotBlank(annotation.action())) {
				routing.parseActionAndFormat(annotation.action());
			}

			answer.add(routing);
		}

		return answer;
	}

	private List<Routing> getRoutings(Class<? extends Action> actionClass) {
		List<Routing> answer = new ArrayList<Routing>();
		Routing routing = actionClass.getAnnotation(Routing.class);
		if(routing != null) {
			answer.add(routing);
		}

		Routings routings = actionClass.getAnnotation(Routings.class);
		if(routings != null) {
			answer.addAll(Arrays.asList(routings.value()));
		}

		return answer;
	}

	private ActionRouting buildPackageBaseName(Class<? extends Action> actionClass, String basePackage) {
		String url = buildPackagePath(actionClass, basePackage) + buildActionPath(actionClass);
		return new ActionRouting(url);
	}

	private String buildActionPath(Class<? extends Action> actionClass) {
		String actionName = actionClass.getSimpleName();
		if(actionName.endsWith(getActionSuffix())) {
			actionName = StringUtils.substringBeforeLast(actionName, getActionSuffix());
		}

		return MessageFormat.format("/{0}.{1}",
				StringUtility.underscores(actionName, "-").toLowerCase(),
				getDefaultExtension());
	}

	private String buildPackagePath(Class<? extends Action> actionClass, String basePackage) {
		String packageName = actionClass.getPackage().getName().substring(basePackage.length());
		packageName = StringUtils.replace(packageName, "_", getSeparator());
		packageName = StringUtils.replace(packageName, ".", "/");
		return packageName;
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public String getDefaultExtension() {
		return defaultExtension;
	}

	@Inject(value="flyer.default.extension", required=false)
	public void setDefaultExtension(String defaultExtension) {
		this.defaultExtension = defaultExtension;
	}

	public String getSeparator() {
		return separator;
	}

	@Inject(value="flyer.path.separator", required=false)
	public void setSeparator(String separator) {
		this.separator = separator;
	}

	@Inject(value="flyer.action.suffix", required=false)
	public void setActionSuffix(String actionSuffix) {
		this.actionSuffix = actionSuffix;
	}

	private String getActionSuffix() {
		return actionSuffix;
	}
}
