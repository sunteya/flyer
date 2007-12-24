/**
 * Created on 2007-8-1
 * Created by Sunteya
 */
package com.sunteya.flyer.acegi.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;

/**
 * @author Sunteya
 *
 */
public class MergingRoutingInvocationDefinitionSource implements RoutingInvocationDefinitionSource {
	private List<RoutingInvocationDefinitionSource> sources = new ArrayList<RoutingInvocationDefinitionSource>();

	@SuppressWarnings("unchecked")
	public ConfigAttributeDefinition getAttributes(Object object) throws IllegalArgumentException {
		ConfigAttributeDefinition answer = new ConfigAttributeDefinition();

        for (RoutingInvocationDefinitionSource source : getSources()) {
        	ConfigAttributeDefinition aDefinition = source.getAttributes(object);
        	if(aDefinition == null) {
        		continue;
        	}

        	for (Iterator<ConfigAttribute> iter = aDefinition.getConfigAttributes(); iter.hasNext();) {
				answer.addConfigAttribute(iter.next());
			}
		}

        return answer;
	}

	@SuppressWarnings("unchecked")
	public Iterator getConfigAttributeDefinitions() {
        Set<ConfigAttributeDefinition> set = new HashSet<ConfigAttributeDefinition>();

        for (RoutingInvocationDefinitionSource source : getSources()) {
        	Iterator<ConfigAttributeDefinition> iter = source.getConfigAttributeDefinitions();
        	while(iter.hasNext()) {
        		set.add(iter.next());
        	}
		}

        return set.iterator();
	}

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		for (RoutingInvocationDefinitionSource source : getSources()) {
			if(!source.supports(clazz)) {
				return false;
			}
		}

		return true;
	}

	public List<RoutingInvocationDefinitionSource> getSources() {
		return sources;
	}

	public void setSources(List<RoutingInvocationDefinitionSource> sources) {
		this.sources = sources;
	}
}
