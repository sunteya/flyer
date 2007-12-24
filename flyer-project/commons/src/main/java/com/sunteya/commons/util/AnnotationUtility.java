/**
 * Created on 2007-7-23
 * Created by Sunteya
 */
package com.sunteya.commons.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sunteya
 *
 */
public class AnnotationUtility {

	public static Field getAnnotationField(Class<?> clazz, Class<? extends Annotation> annotationClass) {
		for (Field field : clazz.getDeclaredFields()) {
			if(field.getAnnotation(annotationClass) != null) {
				return field;
			}
		}

		if(clazz.getSuperclass() != null) {
			return getAnnotationField(clazz.getSuperclass(), annotationClass);
		}

		return null;
	}

	public static Field getAnnotationField(Object obj, Class<? extends Annotation> annotationClass) {
		return getAnnotationField(obj.getClass(), annotationClass);
	}

	public static <T extends Annotation> List<T> findAnnotations(Class<?> clazz, Class<T> annotationClass) {
		List<T> answer = new ArrayList<T>();
		answer.addAll(findAnnotationsInType(clazz, annotationClass));
		answer.addAll(findAnnotationsInField(clazz, annotationClass));
		answer.addAll(findAnnotationsInMethod(clazz, annotationClass));
		return answer;
	}

	public static <T extends Annotation> List<T> findAnnotationsInType(Class<?> clazz, Class<T> annotationClass) {
		List<T> answer = new ArrayList<T>();
		T anno = clazz.getAnnotation(annotationClass);

		if(anno != null) {
			answer.add(anno);
		}

		if(clazz.getSuperclass() != null) {
			answer.addAll(findAnnotationsInType(clazz.getSuperclass(), annotationClass));
		}

		return answer;
	}

	public static <T extends Annotation> List<T> findAnnotationsInField(Class<?> clazz, Class<T> annotationClass) {
		List<T> answer = new ArrayList<T>();

		for (Field field : clazz.getDeclaredFields()) {
			T anno = field.getAnnotation(annotationClass);
			if(anno != null) {
				answer.add(anno);
			}
		}

		if(clazz.getSuperclass() != null) {
			answer.addAll(findAnnotationsInField(clazz.getSuperclass(), annotationClass));
		}

		return answer;
	}

	public static <T extends Annotation> List<T> findAnnotationsInMethod(Class<?> clazz, Class<T> annotationClass) {
		List<T> answer = new ArrayList<T>();

		for (Method method : clazz.getDeclaredMethods()) {
			T anno = method.getAnnotation(annotationClass);
			if(anno != null) {
				answer.add(anno);
			}
		}

		if(clazz.getSuperclass() != null) {
			answer.addAll(findAnnotationsInField(clazz.getSuperclass(), annotationClass));
		}

		return answer;
	}
}
