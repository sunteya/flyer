/**
 * Created on 2007-7-31
 * Created by Sunteya
 */
package com.sunteya.flyer.acegi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.vote.AccessDecisionVoter;
import org.apache.commons.lang.StringUtils;

/**
 * @author Sunteya
 *
 */
public class RoleConditionVoter implements AccessDecisionVoter {

    private String rolePrefix = "ROLE_";
    private ConditionFactory factory;

    public boolean supports(ConfigAttribute attribute) {
        return getAuthority(attribute).startsWith(getRolePrefix());
    }

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return ConditionInvocation.class.isAssignableFrom(clazz);
	}

	@SuppressWarnings("unchecked")
	public int vote(Authentication authentication, Object object, ConfigAttributeDefinition config) {
        int result = ACCESS_ABSTAIN;
        Iterator<ConfigAttribute> iter = config.getConfigAttributes();

        while (iter.hasNext()) {
            ConfigAttribute attribute = iter.next();

            if (this.supports(attribute)) {
                result = ACCESS_DENIED;

                if(isAllow(authentication, object, attribute)) {
                	return ACCESS_GRANTED;
                }
            }
        }

        return result;
	}

	protected boolean isAllow(Authentication authentication, Object object, ConfigAttribute attribute) {
		String authority = getAuthority(attribute);
		List<String> conditions = findConditions(attribute);
		List<Object> domainModels = getDomainModels(object);

		for(GrantedAuthority test : authentication.getAuthorities()) {
			if(authority.equals(test.getAuthority()) && isDomainsAllow(authentication, authority, domainModels, conditions)) {
				return true;
			}
		}

		return false;
	}

	protected boolean isDomainsAllow(Authentication authentication, String authority, List<Object> domainModels, List<String> conditions) {
		if(conditions.isEmpty()) {
			return true;
		}

		for (Object domainModel : domainModels) {
			if(!isDomainAllow(authentication, authority, domainModel, conditions)) {
				return false;
			}
		}

		return true;
	}

	protected boolean isDomainAllow(Authentication authentication, String authority, Object domainModel, List<String> conditions) {
		for (String condString : conditions) {
			Condition condition = factory.createByString(authority, condString);
			if(condition.isTrue(authentication, domainModel)) {
				return true;
			}
		}

		return false;
	}

	protected String getAuthority(ConfigAttribute attribute) {
		String express = attribute.getAttribute();
		return StringUtils.contains(express, "[") ? StringUtils.substringBefore(express, "[") : express;
	}

	protected List<String> findConditions(ConfigAttribute attribute) {
		List<String> answer = new ArrayList<String>();
		String express = attribute.getAttribute();
		if(StringUtils.contains(express, "[")) {
			String conditionsString = StringUtils.substringBetween(express, "[", "]");
			String[] conditions = StringUtils.split(StringUtils.deleteWhitespace(conditionsString), ",");
			answer.addAll(Arrays.asList(conditions));
		}

		return answer;
	}

	protected List<Object> getDomainModels(Object object) {
		return ((ConditionInvocation) object).getDomainModels();
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

    public String getRolePrefix() {
        return rolePrefix;
    }

	public ConditionFactory getFactory() {
		return factory;
	}

	public void setFactory(ConditionFactory factory) {
		this.factory = factory;
	}
}
