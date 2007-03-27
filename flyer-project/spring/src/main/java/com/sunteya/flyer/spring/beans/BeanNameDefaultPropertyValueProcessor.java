/**
 * Created on 2006-12-31
 * Created by Sunteya
 */
package com.sunteya.flyer.spring.beans;

import org.springframework.beans.factory.config.BeanDefinitionHolder;

import com.sunteya.flyer.spring.beans.AbstractDefaultBeanPropertyProcessor;

/**
 * @author Sunteya
 *
 */
public class BeanNameDefaultPropertyValueProcessor extends
		AbstractDefaultBeanPropertyProcessor {

	protected Object getPropertyValue(BeanDefinitionHolder holder,
			String propertyName) {
		return holder.getBeanName();
	}
}
