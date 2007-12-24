/**
 * Created on 2008-1-29
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.sunteya.commons.util.UrlUtils;
import com.sunteya.flyer.web.route.ActionRouting;
import com.sunteya.flyer.web.route.ActionRoutingHolder;
import com.sunteya.flyer.web.route.UrlBuilderHelper;

/**
 * @author Sunteya
 *
 */
public class FlyerActionMapper implements ActionMapper {

	@SuppressWarnings("unchecked")
	public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager configManager) {
		ActionRouting route = ActionRoutingHolder.peekLastRouting();
		String namespace = route.getPackageAndController() == null ? "" : route.getPackageAndController();
		String name = route.getActionAndFormat();
		
		Map<String, ActionConfig> actions = (Map<String, ActionConfig>) configManager.getConfiguration().getRuntimeConfiguration().getActionConfigs().get(namespace);
		
		if(actions == null) {
			return null;
		}
		
		if(!actions.containsKey(name)) {
			return null;
		}
		
		
		ActionMapping answer = new ActionMapping();
		answer.setNamespace(namespace);
		answer.setName(name);
		return answer;
	}

    @SuppressWarnings("unchecked")
	public String getUriFromActionMapping(ActionMapping mapping) {
        StringBuffer uri = new StringBuffer();

        if (!"/".equals(mapping.getNamespace())) {
        	uri.append(mapping.getNamespace());
        }

        uri.append("/");
        String name = mapping.getName();
        uri.append(name);

        Map<String, Object> params = mapping.getParams();
        if(MapUtils.isNotEmpty(params)) {
        	uri.append("?");
        	uri.append(buildQueryString(params));
        }

        return uri.toString();
    }

	private String buildQueryString(final Map<String, Object> params) {
		return UrlUtils.buildQueryString(UrlBuilderHelper.buildParams(params), "utf-8");
	}
}
