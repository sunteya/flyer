/**
 * Created on 2007-8-2
 * Created by Sunteya
 */
package com.sunteya.flyer.acegi.web;

import java.io.BufferedReader;
import java.io.StringReader;

import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.SecurityConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author Sunteya
 *
 */
public class StaticPathRoutingProviderFactory implements FactoryBean, InitializingBean {

	private StaticPathRoutingProvider provider = new StaticPathRoutingProvider();
	private String text;

	public Object getObject() throws Exception {
		return provider;
	}

	@SuppressWarnings("unchecked")
	public Class getObjectType() {
		return StaticPathRoutingProvider.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(getText());
		BufferedReader br = new BufferedReader(new StringReader(getText()));

		while(true) {
			String line = br.readLine();
			if(line == null) {
				break;
			}
			line = line.trim();
			if(StringUtils.isBlank(line)) {
				continue;
			}

			String antPath = StringUtils.substringBefore(line, "=");

			ConfigAttributeDefinition definition = new ConfigAttributeDefinition();
			String express = StringUtils.substringAfter(line, "=");
			for (String role : StringUtils.split(express, ", ")) {
				definition.addConfigAttribute(new SecurityConfig(role));
			}

			provider.addSecureUrl(antPath, definition);
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
