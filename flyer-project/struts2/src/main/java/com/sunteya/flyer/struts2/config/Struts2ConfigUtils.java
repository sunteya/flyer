/**
 * Created on 2007-6-8
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.config.entities.ResultTypeConfig;

/**
 * @author Sunteya
 *
 */
public abstract class Struts2ConfigUtils {

	@SuppressWarnings("unchecked")
	public static ResultConfig buildResultConfig(String resultCode, String location, ResultTypeConfig resultTypeConfig) {
		Map<String, Object> configParams = resultTypeConfig.getParams();
        String className = resultTypeConfig.getClazz();

        Map<String, Object> params = new HashMap<String, Object>();
        if (configParams != null) {
            params.putAll(configParams);
        }

		params.put("location", location);
		return new ResultConfig(resultCode, className, params);
	}

	public static ResultTypeConfig getResultType(String extension, PackageConfig config) {
		Map<String, ResultTypeConfig> resultTypeConfigs = config.getAllResultTypeConfigs();
		if(resultTypeConfigs.containsKey(extension)) {
			return resultTypeConfigs.get(extension);
		}

		return resultTypeConfigs.get(config.getFullDefaultResultType());
	}

	public static ResultConfig getReturnConfig(String resultCode, String location, PackageConfig config) {
		String extension = StringUtils.substringAfterLast(location, ".");
		ResultTypeConfig resultType = getResultType(extension, config);
		return buildResultConfig(resultCode, location, resultType);
	}

}
