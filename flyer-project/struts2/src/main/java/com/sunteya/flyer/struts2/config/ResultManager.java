/**
 * Created on 2008-1-11
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.config.entities.ResultTypeConfig;
import com.opensymphony.xwork2.inject.Inject;
import com.sunteya.flyer.struts2.result.ResultExistable;
import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public class ResultManager {
	
	private Configuration configuration;
	private ObjectFactory objectFactory;
	private FormatTranslator formatTranslator; 
	
	public Result findResult(ActionContext actionContext, String actionName, ActionConfig actionConfig, String resultCode) {
		String namespace = actionContext.getActionInvocation().getProxy().getNamespace();
		ActionRouting routing = new ActionRouting();
		routing.parsePackageAndController(namespace);
		routing.parseActionAndFormat(actionName);
		
		PackageConfig pkg = configuration.getPackageConfig(actionConfig.getPackageName());
		String translated = formatTranslator.translate(routing.getFormat());
		
		ResultTypeConfig typeConfig = findResultTypeConfig(pkg, translated);
		if (typeConfig != null) {
			routing.setFormat(translated);
			Result result = buildResult(routing.toPath(), resultCode, typeConfig, actionContext);
			
			if(result instanceof ResultExistable && ((ResultExistable) result).exists(actionContext)) {
				return buildResult(routing.toPath(), resultCode, typeConfig, actionContext);
			}
		}
		return null;
	}
	
	private ResultTypeConfig findResultTypeConfig(PackageConfig pkg, String format) {
		String resultTypeName = format;
		if (StringUtils.isBlank(resultTypeName)) {
			resultTypeName = pkg.getDefaultResultType();
		}

		return pkg.getAllResultTypeConfigs().get(resultTypeName);
	}

	
	@SuppressWarnings("unchecked")
	protected Result buildResult(String path, String resultCode, ResultTypeConfig config, ActionContext invocationContext) {
		String resultClass = config.getClazz();

		Map<String, String> params = new LinkedHashMap<String, String>();
		if (config.getParams() != null) {
			params.putAll(config.getParams());
		}
		params.put(config.getDefaultResultParam(), path);

		ResultConfig resultConfig = new ResultConfig(resultCode, resultClass, params);
		try {
			return objectFactory.buildResult(resultConfig, invocationContext.getContextMap());
		} catch (Exception e) {
			throw new XWorkException("Unable to build codebehind result", e, resultConfig);
		}
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public Configuration getConfiguration() {
		return configuration;
	}

	@Inject
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public ObjectFactory getObjectFactory() {
		return objectFactory;
	}

	public FormatTranslator getFormatTranslator() {
		return formatTranslator;
	}

	@Inject
	public void setFormatTranslator(FormatTranslator formatTranslator) {
		this.formatTranslator = formatTranslator;
	}

	@Inject
	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}
}
