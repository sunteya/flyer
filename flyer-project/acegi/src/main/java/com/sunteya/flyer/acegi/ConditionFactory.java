/**
 * Created on 2007-8-1
 * Created by Sunteya
 */
package com.sunteya.flyer.acegi;

/**
 * @author Sunteya
 *
 */
public interface ConditionFactory {
	Condition createByString(String authority, String condition);
}
