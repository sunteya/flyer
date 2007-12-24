/**
 * Created on 2007-6-13
 * Created by Sunteya
 */
package com.sunteya.flyer.web.route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.sunteya.flyer.domain.Entity;
import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public class UrlBuilderHelper {

	public static final String CONTROLLER_KEY = "controller";
	public static final String ACTION_KEY = "action";

	public static ActionRouting buildActionMapping(ActionRouting routing, Map<String, Object> option) {
		ActionRouting answer = (ActionRouting) routing.clone();

		if(option.containsKey(CONTROLLER_KEY)) {
			answer.parsePackageAndController(option.remove(CONTROLLER_KEY).toString());
		}

		if(option.containsKey(ACTION_KEY)) {
			answer.parseActionAndFormat(option.remove(ACTION_KEY).toString());
		}

		answer.setParams(buildParams(option));
		return answer;
	}

	public static String paramToString(Object param) {
		Object value = param;
		if(param instanceof Entity) {
			Entity entity = (Entity) param;
			value = entity.getIdentityCode();
		}

		return ObjectUtils.toString(value);
	}

	public static Map<String, String[]> buildParams(Map<String, Object> option) {
		Map<String, String[]> answer = new HashMap<String, String[]>();

		for (String key : option.keySet()) {
			Object obj = option.get(key);
			answer.put(key, buildParam(obj));
		}

		return answer;
	}

	@SuppressWarnings("unchecked")
	protected static String[] buildParam(Object obj) {
		if(obj == null) {
			return  new String[0];
		}

		Object[] params = new Object[] { obj };

		if(obj instanceof Collection) {
			Collection<?> collection = (Collection<?>) obj;
			params = collection.toArray();
		}

		if (obj.getClass().isArray()) {
			params = (Object[]) obj;
		}

		List<String> answer = new ArrayList<String>();
		for (Object param : params) {
			answer.add(paramToString(param));
		}
		return answer.toArray(new String[0]);
	}
}
