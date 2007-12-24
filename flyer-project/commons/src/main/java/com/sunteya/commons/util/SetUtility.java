/**
 * Created on 2007-6-21
 * Created by Sunteya
 */
package com.sunteya.commons.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Sunteya
 *
 */
public abstract class SetUtility {
	public static <T> Set<T> asHashSet(T...args) {
		return new HashSet<T>(Arrays.asList(args));
	}
}
