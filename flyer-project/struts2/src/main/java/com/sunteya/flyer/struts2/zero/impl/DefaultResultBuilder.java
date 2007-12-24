/**
 * Created on 2007-9-21
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.zero.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.config.entities.ResultTypeConfig;
import com.sunteya.commons.util.AnnotationUtility;
import com.sunteya.flyer.struts2.zero.ResultBuilder;
import com.sunteya.flyer.struts2.zero.annotation.Result;
import com.sunteya.flyer.struts2.zero.annotation.Results;

/**
 * @author Sunteya
 *
 */
public class DefaultResultBuilder implements ResultBuilder {

	public Map<String, ResultConfig> build(Class<? extends Action> actionClass, PackageConfig packageConfig) {
		Map<String, ResultConfig> answer = new HashMap<String, ResultConfig>();
		answer.putAll(packageConfig.getGlobalResultConfigs());
		for (Result annonation : getResults(actionClass)) {
			ResultTypeConfig typeConfig = getResultTypeConfig(annonation, packageConfig);

			ResultConfig config = new ResultConfig(
						annonation.name(),
						typeConfig.getClazz(),
						getParams(annonation, typeConfig)
				);

			answer.put(config.getName(), config);
		}

		return answer;
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getParams(Result annonation, ResultTypeConfig typeConfig) {
		Map<String, String> answer = new HashMap<String, String>();
		if(MapUtils.isNotEmpty(typeConfig.getParams())) {
			answer.putAll(typeConfig.getParams());
		}

		for (int i = 0; i < (annonation.params().length / 2); i++) {
			String key = annonation.params()[i * 2];
			String value = annonation.params()[i * 2 + 1];
			answer.put(key, value);
		}

		answer.put(typeConfig.getDefaultResultParam(), annonation.location());
		return answer;
	}

	private ResultTypeConfig getResultTypeConfig(Result annonation, PackageConfig packageConfig) {
		String typeName = annonation.type();
		if(StringUtils.isBlank(typeName)) {
			typeName = packageConfig.getFullDefaultResultType();
		}

		return packageConfig.getAllResultTypeConfigs().get(typeName);
	}

	private List<Result> getResults(Class<? extends Action> actionClass) {
		List<Result> answer = new ArrayList<Result>();
		answer.addAll(AnnotationUtility.findAnnotations(actionClass, Result.class));

		List<Results> resultsList = AnnotationUtility.findAnnotations(actionClass, Results.class);
		for (Results results : resultsList) {
			answer.addAll(Arrays.asList(results.value()));
		}

		return answer;
	}

}
