/**
 * Created on 2007-3-8
 * Created by Sunteya
 */
package com.sunteya.commons.util;

import java.util.Comparator;
import java.util.Map;

/**
 * @author Sunteya
 *
 */
public class OrderMapComparator<T> implements Comparator<T> {

	private Map<T, Integer> orderMap;
	private Integer defaultValue;

	public OrderMapComparator(Map<T, Integer> orderMap) {
		this(orderMap, -1);
	}

	public OrderMapComparator(Map<T, Integer> orderMap, Integer defaultValue) {
		this.orderMap = orderMap;
		this.defaultValue = defaultValue;
	}

	public int compare(T k1, T k2) {
		Integer o1 = getValue(k1);
		Integer o2 = getValue(k2);

		return o1 - o2;
	}

	private Integer getValue(T key) {
		return orderMap.containsKey(key) ? orderMap.get(key) : defaultValue;
	}
}
