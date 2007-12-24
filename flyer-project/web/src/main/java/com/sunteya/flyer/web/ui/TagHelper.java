/**
 * Created on 2007-6-18
 * Created by Sunteya
 */
package com.sunteya.flyer.web.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Sunteya
 *
 */
public class TagHelper {

	public static Map<String, Object> margeClass(Map<String, Object> option, String className) {
		Map<String, Object> answer = new HashMap<String, Object>(option);
		String classNames = ObjectUtils.toString(answer.remove("class"));

		if(StringUtils.isBlank(classNames)) {
			classNames = className;
		} else {
			classNames += " " +  className;
		}

		answer.put("class", classNames);
		return answer;
	}
	
	public static Map<String, Object> margeClassIf(boolean condition, Map<String, Object> option, String className) {
		return condition? margeClass(option, className) : option;
	}

	public static TagHelper newObject() {
		return new TagHelper();
	}

	public String contentTag(String name, String content, Map<String, Object> option) {
		StringBuilder target = new StringBuilder();
		target.append(tag(name, option, true));
		target.append(content);
		target.append("</" + name + ">");
		return target.toString();
	}

	public String tag(String name, Map<String, Object> option, boolean open) {
		StringBuilder target = new StringBuilder();
		target.append("<" + name);
		target.append(tagOptions(option));
		if(open) {
			target.append(">");
		} else {
			target.append(" />");
		}

		return target.toString();
	}

	public static String tagOptions(Map<String, Object> option) {
		if(MapUtils.isEmpty(option)) {
			return "";
		}

		StringBuilder target = new StringBuilder();
		for (Map.Entry<String, Object> entry : option.entrySet()) {
			target.append(" " + entry.getKey());
			target.append("=\"" + StringEscapeUtils.escapeXml(entry.getValue().toString()) + "\"");
		}

		return target.toString();
	}
}
