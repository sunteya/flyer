/**
 * Created on 2007-7-11
 * Created by Sunteya
 */
package com.sunteya.flyer.acegi;

import java.util.List;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.InsufficientAuthenticationException;
import org.acegisecurity.vote.AbstractAccessDecisionManager;
import org.acegisecurity.vote.AccessDecisionVoter;

/**
 * @author Sunteya
 *
 */
public class ObjectionBased extends AbstractAccessDecisionManager {

	@SuppressWarnings("unchecked")
	public void decide(Authentication authentication, Object object, ConfigAttributeDefinition config) throws AccessDeniedException, InsufficientAuthenticationException {
		int grant = 0;

		for (AccessDecisionVoter voter : (List<AccessDecisionVoter>) getDecisionVoters()) {
			int result = voter.vote(authentication, object, config);

			switch (result) {
			case AccessDecisionVoter.ACCESS_DENIED:
				throw new AccessDeniedException(messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
			case AccessDecisionVoter.ACCESS_GRANTED:
				grant++;
			}
		}

		if(grant == 0) {
			checkAllowIfAllAbstainDecisions();
		}
	}
}
