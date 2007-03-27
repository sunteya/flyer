/**
 * Created on 2007-3-19
 * Created by Sunteya
 */
package com.sunteya.flyer.test.hibernate;

import java.io.StringWriter;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
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
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(IgnoreNotNullConfiguration.class);

	private static final long serialVersionUID = 1892287556377426642L;

	@Override
	protected void add(Document doc) throws MappingException {

		disableNotNull(doc);
		logDoucment(doc);

		super.add(doc);
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

	@SuppressWarnings("unchecked")
	private void disableNotNull(Document doc) {
		List<Attribute> results = doc.selectNodes("//property/@not-null[.=\"true\"]");
		for (Attribute attrib : results) {
			attrib.setData(false);
		}
	}
}
