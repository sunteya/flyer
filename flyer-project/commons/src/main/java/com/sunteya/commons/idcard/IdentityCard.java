/**
 * Created on 2006-6-29
 * Created by Sunteya
 */
package com.sunteya.commons.idcard;

import java.util.Date;

/**
 *
 * @author 诸南敏
 * @email zhunm@dep5.com
 */
public interface IdentityCard {

	String getIdentityCode();

	int getIdentityCodeLength();

	String getRegionCode();

	Date getBirthday();

	String getSerialNumber();

	boolean isMale();

	boolean isFemale();
}