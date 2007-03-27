/**
 * Created on 2007-2-6
 * Created by Sunteya
 */
package com.sunteya.flyer.web.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.StringUtil;

/**
 * @author Sunteya
 *
 */
public class UrlLinkMethod implements TemplateMethodModel {
	private EncodeUrlMethod encodeUrlMethod = new EncodeUrlMethod();

	@SuppressWarnings("unchecked")
	public Object exec(List arguments) throws TemplateModelException {
		String rs = (String) encodeUrlMethod.exec(arguments);
		return StringUtil.HTMLEnc(rs);
	}
}
