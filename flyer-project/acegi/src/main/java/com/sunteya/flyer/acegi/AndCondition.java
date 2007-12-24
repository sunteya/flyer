/**
 * Created on 2007-8-1
 * Created by Sunteya
 */
package com.sunteya.flyer.acegi;

import java.util.List;

import org.acegisecurity.Authentication;


/**
 * @author Sunteya
 *
 */
public class AndCondition implements Condition {

	private List<? extends Condition> conditions;

	public AndCondition(List<? extends Condition> conditions) {
		this.conditions = conditions;
	}

	public boolean isTrue(Authentication authentication, Object obj) {
		for (Condition condition : getConditions()) {
			if(!condition.isTrue(authentication, obj)) {
				return false;
			}
		}

		return true;
	}

	public List<? extends Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<? extends Condition> conditions) {
		this.conditions = conditions;
	}
}
