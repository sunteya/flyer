/**
 * Created on 2007-1-26
 * Created by Lewisou
 */
package com.sunteya.flyer.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Lewisou
 *
 */
public abstract class UrlUtils {

	public static String getBaseUrl(String url) {
		return StringUtils.split(url, "?#")[0];
	}

	public static String getAnchor(String url) {
		String anchor = StringUtils.substringAfter(url, "#");
		if (StringUtils.isBlank(anchor)) {
			return null;
		}
		return anchor;
	}

	public static Map<String, String[]> getParameters(String url,
			String encoding) {
		String queryString = getQueryString(url);

		Map<String, String[]> answer = new HashMap<String, String[]>();
		String[] params = StringUtils.split(queryString, "&");
		if (params == null) {
			return answer;
		}

		for (String param : params) {
			String decodedKey = decodeUrl(StringUtils.substringBefore(param,
					"="), encoding);
			String decodedValue = decodeUrl(StringUtils.substringAfter(param,
					"="), encoding);

			String[] values = answer.get(decodedKey);
			if (values == null) {
				values = new String[] { decodedValue };
			} else {
				values = (String[]) ArrayUtils.add(values, decodedValue);
			}

			answer.put(decodedKey, values);
		}

		return answer;
	}

	@SuppressWarnings("deprecation")
	public static String decodeUrl(String source, String encoding) {
		if (source == null) {
			return null;
		}

		try {
			return URLDecoder.decode(source, encoding);
		} catch (UnsupportedEncodingException e) {
			return URLDecoder.decode(source);
		}
	}

	public static String getQueryString(String url) {
		String queryString = null;
		if (getAnchor(url) == null) {
			queryString = StringUtils.substringAfter(url, "?");
		} else {
			queryString = StringUtils.substringBetween(url, "?", "#");
		}

		if (StringUtils.isBlank(queryString)) {
			return null;
		}

		return queryString;
	}

	public static Map<String, String> getSimpleParameters(String url,
			String encoding) {
		Map<String, String> answer = new HashMap<String, String>();
		Map<String, String[]> params = getParameters(url, encoding);
		for (String key : (Set<String>) params.keySet()) {
			answer.put(key, params.get(key)[0]);
		}

		return answer;
	}

	public static String appendQueryProperties(String url, Map<String, String[]> properties, String encoding) {
		Map<String, String[]> params = getParameters(url, encoding);

		for (String key : (Set<String>) properties.keySet()) {
			String[] values = properties.get(key);
			if(params.containsKey(key)) {
				String[] existValues = params.get(key);
				values = (String[]) ArrayUtils.addAll(existValues, values);
			}

			params.put(key, values);
		}


		String baseUrl = getBaseUrl(url);
		String anchor = getAnchor(url);
		return buildUrl(baseUrl, params, anchor, encoding);
	}

	public static String buildUrl(String baseUrl, Map<String, String[]> params, String anchor, String encoding) {
		String queryString = buildQueryString(params, encoding);

		StringBuilder targetUrl = new StringBuilder();
		targetUrl.append(baseUrl);
		if(StringUtils.isNotBlank(queryString)) {
			targetUrl.append("?");
			targetUrl.append(queryString);
		}

		if(StringUtils.isNotBlank(anchor)) {
			targetUrl.append("#");
			targetUrl.append(anchor);
		}

		return targetUrl.toString();
	}

	public static String buildQueryString(Map<String, String[]> params, String encoding) {
		List<String> answer = new ArrayList<String>();
		for (String key : (Set<String>) params.keySet()) {
			String[] values = params.get(key);
			for(String value : values) {
				String encodeKey = encodeUrl(key, encoding);
				String encodeValue = encodeUrl(value, encoding);
				answer.add(encodeKey + "=" + encodeValue);
			}
		}
		String queryString = StringUtils.join(answer.iterator(), "&");
		return queryString;
	}

	@SuppressWarnings("deprecation")
	public static String encodeUrl(String key, String encoding) {
		try {
			return URLEncoder.encode(key, encoding);
		} catch (UnsupportedEncodingException e) {
			return URLEncoder.encode(key);
		}
	}

	public static String replaceParameter(String url, String name, String value, String encoding) {
		Map<String, String[]> params = getParameters(url, encoding);
		params.put(name, new String[] { value });

		String baseUrl = getBaseUrl(url);
		String anchor = getAnchor(url);
		return buildUrl(baseUrl, params, anchor, encoding);
	}

	public static String buildUrl(String baseUrl, Map<String, String[]> params, String encoding) {
		return buildUrl(baseUrl, params, null, encoding);
	}

	public static String getRootUrl(String url) {
		if(!StringUtils.contains(url, "://")) {
			return null;
		}

		String protocol = StringUtils.substringBefore(url, "://");
		String urlWithoutProtocol = StringUtils.substringAfter(url, "://");

		String serverAndPort = StringUtils.substringBefore(urlWithoutProtocol, "/");

		return protocol + "://" + serverAndPort + "/";
	}
}
