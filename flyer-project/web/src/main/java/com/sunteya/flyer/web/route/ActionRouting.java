/**
 * Created on 2007-6-17
 * Created by Sunteya
 */
package com.sunteya.flyer.web.route;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.sunteya.commons.util.UrlUtils;

/**
 * @author Sunteya
 *
 */
public class ActionRouting implements Serializable, Cloneable {

	private static final long serialVersionUID = -1977165080708562101L;

	private String pkg;
	private String controller;
	private String action;
	private String format;
	private Map<String, String[]> params = new HashMap<String, String[]>();

	public ActionRouting() {
	}

	@Override
	public Object clone() {
		return new ActionRouting(this);
	}

	public ActionRouting(String url) {
		String path = StringUtils.substringBeforeLast(url, "?");
		String queryString = StringUtils.substringAfterLast(url, "?");
		parsePath(path);
		parseQueryString(queryString);
	}

	public ActionRouting(ActionRouting other) {
		this.pkg = other.pkg;
		this.controller = other.controller;
		this.action = other.action;
		this.format = other.format;
		this.params = new HashMap<String, String[]>(other.params);
	}


	public void parseQueryString(String queryString) {
		if(StringUtils.isBlank(queryString)) {
			return;
		}

		String[] params = StringUtils.split(queryString, "&");
		for (int i = 0; i < params.length; i++) {
			String param = params[i];
			String name = StringUtils.substringBeforeLast(param, "=");
			String value = StringUtils.substringAfterLast(param, "=");

			addParam(name, value);
		}
	}

	public void parsePath(String path) {
		if(StringUtils.contains(path, "/")) {
			parsePackageAndController(StringUtils.substringBeforeLast(path, "/"));
			parseActionAndFormat(StringUtils.substringAfterLast(path, "/"));
		} else {
			parseActionAndFormat(path);
		}
	}

	public void parsePackageAndController(String pnc) {
		if(StringUtils.contains(pnc, "/")) {
			setPackage(StringUtils.substringBeforeLast(pnc, "/"));
			setController(StringUtils.substringAfterLast(pnc, "/"));
		} else {
			setController(pnc);
		}
	}

	public void parseActionAndFormat(String anf) {
		if(StringUtils.contains(anf, ".")) {
			setAction(StringUtils.substringBeforeLast(anf, "."));
			setFormat(StringUtils.substringAfterLast(anf, "."));
		} else {
			setAction(anf);
		}
	}

	public String getPackageAndController() {
		StringBuilder target = new StringBuilder();
		if(getPackage() != null && !"/".equals(getPackage())) {
			target.append(getPackage());
		}

		if(getController() != null) {
			target.append("/");
			target.append(getController());
		}

		String pnc = target.toString();
		return "".equals(pnc) ? null : pnc;
	}

	public String getActionAndFormat() {
		StringBuilder target = new StringBuilder();
		if(getAction() != null) {
			target.append(getAction());
		}

		if(getFormat() != null) {
			target.append(".");
			target.append(getFormat());
		}

		String answer = target.toString();
		return "".equals(answer) ? null : answer;
	}

	public String toUrl() {
		StringBuilder target = new StringBuilder();
		target.append(toPath());
		if(toQueryString() != null) {
			target.append("?");
			target.append(toQueryString());
		}

		return target.toString();
	}

	public String toPath() {
		StringBuilder target = new StringBuilder();
		if(getPackageAndController() != null) {
			target.append(getPackageAndController());
		}
		target.append("/");
		if(getActionAndFormat() != null) {
			target.append(getActionAndFormat());
		}
		return target.toString();
	}

	public String toQueryString() {
		if(MapUtils.isEmpty(getParams())) {
			return null;
		}

		return UrlUtils.buildQueryString(getParams(), "UTF-8");
	}

	public void addParam(String key, String value) {
		String[] values = getParams().get(key);
		if(values == null) {
			values = new String[0];
		}

		getParams().put(key, (String[]) ArrayUtils.add(values, value));
	}

	public void setParam(String key, String[] value) {
		getParams().put(key, value);
	}

	public void setSingleParam(String key, String value) {
		setParam(key, new String[] { value });
	}

	public String getParamValue(String key) {
		String[] paramValues = getParams().get(key);
		return ArrayUtils.isEmpty(paramValues) ? null : paramValues[0];
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		if("".equals(controller)) {
			this.controller = null;
		} else {
			this.controller = controller;
		}
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		if("".equals(action)) {
			this.action = null;
		} else {
			this.action = action;
		}
	}

	public Map<String, String[]> getParams() {
		return params;
	}

	public void setParams(Map<String, String[]> params) {
		this.params = params;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		if("".equals(format)) {
			this.format = null;
		} else {
			this.format = format;
		}
	}

	public String getPackage() {
		return pkg;
	}

	public void setPackage(String pkg) {
		if("".equals(pkg)) {
			this.pkg = null;
		} else {
			this.pkg = pkg;
		}
	}
}
