/**
 * Created on 2007-8-1
 * Created by Sunteya
 */
package com.sunteya.flyer.acegi.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.acegisecurity.ConfigAttributeDefinition;


/**
 * @author Sunteya
 *
 */
public class StaticPathRoutingProvider implements PathRoutingProvider {

    private List<PathEntryHolder> entries = Collections.synchronizedList(new ArrayList<PathEntryHolder>());

    public List<PathEntryHolder> getEntries() {
		return entries;
	}

	public void addSecureUrl(String antPath, ConfigAttributeDefinition attr) {
	    getEntries().add(new PathEntryHolder(antPath, attr));
	}
}
