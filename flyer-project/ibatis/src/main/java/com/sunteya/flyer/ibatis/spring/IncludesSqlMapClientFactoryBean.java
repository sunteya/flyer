/**
 * Created on 2007-5-18
 * Created by Sunteya
 */
package com.sunteya.flyer.ibatis.spring;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;

import com.ibatis.sqlmap.engine.builder.xml.SqlMapClasspathEntityResolver;

/**
 * @author Sunteya
 *
 */
public class IncludesSqlMapClientFactoryBean extends SqlMapClientFactoryBean {

	private Resource configLocation;

	public void setConfigLocation(Resource configLocation) {
		super.setConfigLocation(configLocation);
		this.configLocation = configLocation;
	}

	public static String INCLUDES_PREFIX = "includes:";

	public void afterPropertiesSet() throws Exception {
		Document source = buildDocument(configLocation.getInputStream());
		Document target = generateSqlMapConfig(source);
		setConfigLocation(toResources(target));

		super.afterPropertiesSet();
	}

	private Resource toResources(Document target)
			throws UnsupportedEncodingException, IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLWriter writer = new XMLWriter(out);
		writer.write(target);

		Resource resource = new ByteArrayResource(out.toByteArray());
		return resource;
	}

	@SuppressWarnings("unchecked")
	private Document generateSqlMapConfig(Document source)
			throws IOException {
		Document target = (Document) source.clone();
		Element targetRoot = (Element) target.node(0);
		targetRoot.clearContent();

		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Element sourceRoot = (Element) source.node(0);
		for (Iterator<Node> iter = sourceRoot.nodeIterator(); iter.hasNext();) {
			Node node = iter.next();
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if("sqlMap".equals(element.getName())) {
					String resourcePath = element.attributeValue("resource");
					if(resourcePath == null) {
						continue;
					}
					if(resourcePath.startsWith(INCLUDES_PREFIX)) {
						String pattern = ResourceLoader.CLASSPATH_URL_PREFIX + resourcePath.substring(INCLUDES_PREFIX.length());
						Resource[] resources = resolver.getResources(pattern);
						for (int i = 0; i < resources.length; i++) {
							Resource resource = resources[i];

							Element sqlmap = targetRoot.addElement("sqlMap");
							sqlmap.addAttribute("url", resource.getURL().toString());
						}

						continue;
					}
				}
			}

			targetRoot.add((Node) node.clone());
		}

		return target;
	}

	private Document buildDocument(InputStream in) throws DocumentException, IOException {
		SAXReader reader = new SAXReader();
		reader.setEntityResolver(new SqlMapClasspathEntityResolver());
		return reader.read(in);
	}

	protected String format(byte[] data) throws Exception {
		Document document = buildDocument(new ByteArrayInputStream(data));
		StringWriter formatWriter = new StringWriter();
		XMLWriter writer = new XMLWriter(formatWriter, new OutputFormat("  ", true));
		writer.write(document);
		return formatWriter.toString();
	}
}
