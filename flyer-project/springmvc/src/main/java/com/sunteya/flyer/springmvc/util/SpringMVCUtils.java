/**
 * Created on 2007-2-9
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.util;

import java.util.Map;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;

import com.sunteya.flyer.support.beans.CustomEnumEditor;

/**
 * @author Sunteya
 *
 */
public abstract class SpringMVCUtils {

	@SuppressWarnings("unchecked")
	public static ModelMap newModelMap(Map objects) {
		ModelMap model = new ModelMap();
		model.addAllObjects(objects);
		return model;
	}

	@SuppressWarnings("unchecked")
	public static void registerEnumEditor(ServletRequestDataBinder binder, Class clazz) {
		binder.registerCustomEditor(clazz, new CustomEnumEditor(clazz));
	}
}
