/**
 * Created on 2006-12-31
 * Created by Sunteya
 */
package com.sunteya.flyer.spring.beans;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;

/**
 * @author Sunteya
 *
 */
public abstract class AbstractDefaultBeanPropertyProcessor implements BeanDefinitionPostProcessor {

	private String[] propertyNames = new String[0];
	private Class<?> beanClass = Object.class;

	public void postProcessBeanDefinition(BeanDefinitionHolder holder, BeanDefinition mergedBeanDefinition) throws IllegalArgumentException {
		for (String propertyName : getPropertyNames()) {
			addDefaultValueIfNeed(holder, mergedBeanDefinition, propertyName);
		}
	}

	private void addDefaultValueIfNeed(BeanDefinitionHolder holder, BeanDefinition mergedBeanDefinition, String propertyName) throws IllegalArgumentException {
		if (needAppendPropertyValue(mergedBeanDefinition, propertyName)) {
			appendPropertyValue(holder, propertyName);
		}
	}

	private void appendPropertyValue(BeanDefinitionHolder holder, String propertyName) {
		BeanDefinition definition = holder.getBeanDefinition();
		MutablePropertyValues mpv = definition.getPropertyValues();
		Object value = getPropertyValue(holder, propertyName);
		mpv.addPropertyValue(propertyName, value);
	}

	protected abstract Object getPropertyValue(BeanDefinitionHolder holder,
			String propertyName);

	private boolean needAppendPropertyValue(BeanDefinition definition, String propertyName) throws IllegalArgumentException {
		try {
			if (definition.isAbstract()) {
				return false;
			}

			Class<?> clazz = Class.forName(definition.getBeanClassName());

			if (getBeanClass().isAssignableFrom(clazz)
					&& isWriteableProperty(clazz, propertyName)
					&& !definition.getPropertyValues().contains(propertyName)) {
				return true;
			}

			return false;

		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	private static boolean isWriteableProperty(Class<?> clazz, String propertyName) {
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(clazz);
		for (PropertyDescriptor pd : pds) {
			if (pd.getName().equals(propertyName)) {
				return true;
			}
		}

		return false;
	}

	public void setPropertyName(String propertyName) {
		setPropertyNames(new String[] { propertyName });
	}

	public String[] getPropertyNames() {
		return propertyNames;
	}

	public void setPropertyNames(String[] propertyNames) {
		this.propertyNames = propertyNames;
	}

	public void setBeanClass(Class<?> clazz) {
		this.beanClass = clazz;
	}

	public Class<?> getBeanClass() {
		return beanClass;
	}
}