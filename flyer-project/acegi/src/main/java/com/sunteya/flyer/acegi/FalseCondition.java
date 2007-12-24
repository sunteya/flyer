/**
 * Created on 2007-8-1
 * Created by Sunteya
 */
package com.sunteya.flyer.acegi;

import org.acegisecurity.Authentication;


/**
 * @author Sunteya
 *
 */
public class FalseCondition implements Condition {

	public boolean isTrue(Authentication authentication, Object obj) {
		return false;
	}

}
