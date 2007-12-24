/**
 * Created on 2007-8-1
 * Created by Sunteya
 */
package com.sunteya.flyer.acegi.web;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.ConfigAttributeDefinition;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;


/**
 * @author Sunteya
 *
 */
public class PathRoutingDefinitionSource implements RoutingInvocationDefinitionSource {

	private PathMatcher pathMatcher = new AntPathMatcher();
	private PathRoutingProvider provider;

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
	    return RoutingInvocation.class.isAssignableFrom(clazz);
	}

	public ConfigAttributeDefinition getAttributes(Object object) throws IllegalArgumentException {
	    if (!supports(object.getClass())) {
	        throw new IllegalArgumentException("Object must be a RoutingInvocation");
	    }

	    String url = ((RoutingInvocation) object).getRouting().toPath();
	    return lookupAttributes(url);
	}

	@SuppressWarnings("unchecked")
	public Iterator getConfigAttributeDefinitions() {
	    Set<ConfigAttributeDefinition> set = new HashSet<ConfigAttributeDefinition>();
	    for (PathEntryHolder holder : getEntries()) {
	    	set.add(holder.getConfigAttributeDefinition());
		}

	    return set.iterator();
	}

	public int getMapSize() {
	    return this.getEntries().size();
	}

	public ConfigAttributeDefinition lookupAttributes(String url) {
		for (PathEntryHolder holder : getEntries()) {
			if (pathMatcher.match(holder.getAntPath(), url)) {
				return holder.getConfigAttributeDefinition();
			}
		}

	    return null;
	}

	private List<PathEntryHolder> getEntries() {
		return getProvider().getEntries();
	}

	public PathRoutingProvider getProvider() {
		return provider;
	}

	public void setProvider(PathRoutingProvider provider) {
		this.provider = provider;
	}
}