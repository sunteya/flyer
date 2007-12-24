/**
 * Created on 2007-7-31
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.acegi;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sunteya.commons.util.AnnotationUtility;
import com.sunteya.commons.util.ReferenceUtility;
import com.sunteya.flyer.acegi.web.MappingTo;
import com.sunteya.flyer.acegi.web.RoutingInvocation;
import com.sunteya.flyer.annotation.DomainModel;
import com.sunteya.flyer.annotation.DomainModels;
import com.sunteya.flyer.support.Pagination;
import com.sunteya.flyer.web.route.ActionRouting;

/**
 * @author Sunteya
 *
 */
public class RoutingInvocationAdaptor implements RoutingInvocation {

	private List<Object> domainModels;
	private ActionRouting routing;

	public RoutingInvocationAdaptor(Object action, ActionRouting originality) {
		routing = convertRoutingIfNeed(action, originality);
		domainModels = findDomainModels(action);
	}

	private ActionRouting convertRoutingIfNeed(Object action, ActionRouting originality) {
		MappingTo mapping = action.getClass().getAnnotation(MappingTo.class);
		if(mapping == null) {
			return originality;
		}

		ActionRouting answer = (ActionRouting) originality.clone();
		answer.getParams().clear();

		if(StringUtils.isNotBlank(mapping.pkg())) {
			answer.setPackage(mapping.pkg());
		}

		if(StringUtils.isNotBlank(mapping.controller())) {
			answer.setController(mapping.controller());
		}

		if(StringUtils.isNotBlank(mapping.action())) {
			answer.setAction(mapping.action());
		}

		if(StringUtils.isNotBlank(mapping.format())) {
			answer.setFormat(mapping.format());
		}

		return answer;
	}

	private List<Object> findDomainModels(Object action) {
		List<Object> answer = new ArrayList<Object>();
		Object model = findSingleDomain(action);
		if(model != null) {
			answer.add(model);
		}
		answer.addAll(findCollectionDomain(action));
		return answer;
	}

	@SuppressWarnings("unchecked")
	private Collection<? extends Object> findCollectionDomain(Object action) {
		List<Object> answer = new ArrayList<Object>();
		Field field = AnnotationUtility.getAnnotationField(action, DomainModels.class);
		if(field == null) {
			return answer;
		}

		Object value = ReferenceUtility.getValue(action, field);
		if(value == null) {
			return answer;
		}

		if(value.getClass().isArray()) {
			for (int i = 0; i < Array.getLength(value); i++) {
				answer.add(Array.get(value, i));
			}
		}

		if(value instanceof Collection) {
			answer.addAll((Collection<Object>) value);
		}

		if(value instanceof Pagination) {
			answer.addAll(((Pagination) value).getElements());
		}

		return answer;
	}

	private Object findSingleDomain(Object action) {
		Field field = AnnotationUtility.getAnnotationField(action, DomainModel.class);
		return field == null ? null : ReferenceUtility.getValue(action, field);
	}

	public List<Object> getDomainModels() {
		return domainModels;
	}

	public ActionRouting getRouting() {
		return routing;
	}

}
