/**
 * Created on 2007-3-19
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate;

import java.io.StringWriter;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.hibernate.MappingException;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sunteya
 *
 */
public class IgnoreNotNullConfiguration extends Configuration {

	private static final Logger logger = LoggerFactory.getLogger(IgnoreNotNullConfiguration.class);

	private static final long serialVersionUID = 1892287556377426642L;

	@Override
	protected void add(Document doc) throws MappingException {
		disableNotNull(doc);
		removeVersions(doc);
		logDoucment(doc);

		super.add(doc);
	}

	@SuppressWarnings("unchecked")
	private void removeVersions(Document doc) {
		List<Element> results = doc.selectNodes("//class/version");
		for (Element element : results) {
			Element parent = element.getParent();
			parent.remove(element);
		}
	}

	private void logDoucment(Document doc) {
		try {
			StringWriter stringWriter = new StringWriter();
			XMLWriter writer = new XMLWriter(stringWriter, new OutputFormat("\t", true));
			writer.write(doc);
			writer.close();
			if (logger.isDebugEnabled()) {
				logger.debug("add(Document) - \n" + stringWriter.toString()); //$NON-NLS-1$
			}
		} catch (Exception e) {
			logger.error("add(Document)", e); //$NON-NLS-1$
		}
	}

	private void disableNotNull(Document doc) {
		disableAttribs(doc, "//property/@not-null[.=\"true\"]");
		disableAttribs(doc, "//many-to-one/@not-null[.=\"true\"]");
		disableAttribs(doc, "//column/@not-null[.=\"true\"]");
	}

	@SuppressWarnings("unchecked")
	private void disableAttribs(Document doc, String xpath) {
		List<Attribute> results = doc.selectNodes(xpath);

		for (Attribute attrib : results) {
			attrib.setData(false);
		}
	}
}
