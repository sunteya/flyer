/**
 * Created on 2006-12-31
 * Created by Sunteya
 */
package com.sunteya.flyer.spring.beans;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;

/**
 * @author Sunteya
 *
 */
public interface BeanDefinitionPostProcessor {

	void postProcessBeanDefinition(BeanDefinitionHolder holder, BeanDefinition mergedBeanDefinition) throws IllegalArgumentException;

}
