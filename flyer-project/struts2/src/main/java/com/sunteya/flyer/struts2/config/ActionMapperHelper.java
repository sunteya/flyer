/**
 * Created on 2007-7-9
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.config;

import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public class ActionMapperHelper {
	public static String getUriFromActionMapping(ActionMapper mapper, ActionRouting routing) {
		return mapper.getUriFromActionMapping(toActionMapping(routing));
	}

	public static ActionMapping toActionMapping(ActionRouting routing) {
		ActionMapping mapping = new ActionMapping();
		String namespace = routing.getPackageAndController() == null ? "" : routing.getPackageAndController();
		mapping.setNamespace(namespace);

		mapping.setName(routing.getActionAndFormat());
		mapping.setParams(routing.getParams());

		return mapping;
	}
}
