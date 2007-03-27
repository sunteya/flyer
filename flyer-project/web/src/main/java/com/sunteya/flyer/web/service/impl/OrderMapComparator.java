/**
 * Created on 2007-3-8
 * Created by Sunteya
 */
package com.sunteya.flyer.web.service.impl;

import java.util.Comparator;
import java.util.Map;

/**
 * @author Sunteya
 *
 */
public class OrderMapComparator<T> implements Comparator<T> {

	private Map<T, Integer> orderMap;

	public OrderMapComparator(Map<T, Integer> orderMap) {
		this.orderMap = orderMap;
	}

	public int compare(T k1, T k2) {
		int o1 = orderMap.get(k1);
		int o2 = orderMap.get(k2);

		return o1 - o2;
	}
}
