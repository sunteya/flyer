/**
 * Created on 2007-3-28
 * Created by Sunteya
 */
package com.sunteya.flyer.web.service;

import java.util.List;
import java.util.SortedMap;

/**
 * @author Sunteya
 *
 */
public interface LabelService {

	@Deprecated
	SortedMap<String, String> getLabelMap(String name);

	List<String> getLabelList(String name);
}