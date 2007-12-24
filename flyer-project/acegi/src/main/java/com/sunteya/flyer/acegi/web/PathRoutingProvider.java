/**
 * Created on 2007-8-8
 * Created by Sunteya
 */
package com.sunteya.flyer.acegi.web;

import java.util.List;


/**
 * @author Sunteya
 *
 */
public interface PathRoutingProvider {
	List<PathEntryHolder> getEntries();
}
