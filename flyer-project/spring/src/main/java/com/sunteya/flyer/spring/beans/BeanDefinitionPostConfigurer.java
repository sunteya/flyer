/**
 * Created on 2006-12-31
 * Created by Sunteya
 */
package com.sunteya.flyer.spring.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.core.Ordered;

/**
 * @author Sunteya
 *
 */
public class BeanDefinitionPostConfigurer implements BeanFactoryPostProcessor,
		Ordered {

	private int order = Ordered.LOWEST_PRECEDENCE;

	private BeanDefinitionPostProcessor[] processors;

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		String[] beanNames = beanFactory.getBeanDefinitionNames();

		for (String beanName : beanNames) {
			BeanDefinition mergedBeanDefinition = getMergedBeanDefinition(beanFactory, beanName);
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
			String[] aliases = beanFactory.getAliases(beanName);

			BeanDefinitionHolder holder = new BeanDefinitionHolder(
					beanDefinition, beanName, aliases);

			for (BeanDefinitionPostProcessor processor : getProcessors()) {
				processor.postProcessBeanDefinition(holder, mergedBeanDefinition);
			}
		}
	}

	private BeanDefinition getMergedBeanDefinition(ConfigurableListableBeanFactory beanFactory, String beanName) {
		if(beanFactory instanceof AbstractBeanFactory) {
			AbstractBeanFactory abstractBeanFactory = (AbstractBeanFactory) beanFactory;
			return abstractBeanFactory.getMergedBeanDefinition(beanName);
		}

		return beanFactory.getBeanDefinition(beanName);
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void setPorcessor(BeanDefinitionPostProcessor processor) {
		setProcessors(new BeanDefinitionPostProcessor[] { processor });
	}

	public void setProcessors(BeanDefinitionPostProcessor[] processors) {
		this.processors = processors;
	}

	public BeanDefinitionPostProcessor[] getProcessors() {
		return processors;
	}

}
