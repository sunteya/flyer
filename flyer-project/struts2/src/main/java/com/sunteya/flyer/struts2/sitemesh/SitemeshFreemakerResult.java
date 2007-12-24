/**
 * Created on 2008-1-3
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.sitemesh;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.Factory;
import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.PageParser;
import com.opensymphony.module.sitemesh.filter.Buffer;
import com.opensymphony.xwork2.ActionInvocation;
import com.sunteya.flyer.struts2.result.FreemarkerResult;

import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * @author Sunteya
 * 
 */
public class SitemeshFreemakerResult extends FreemarkerResult {

	private static final long serialVersionUID = -5676894851707519493L;
	private Factory factory;
	private PageParser pageParser;

	@Override
	protected void prepare(ActionInvocation invocation) {
		super.prepare(invocation);
		setPageParser(getFactory().getPageParser(getContentType()));
	}

	public void execute() throws TemplateModelException, TemplateException,
			IOException {
		preTemplateProcess();
		if (getFactory().shouldParsePage(getContentType())) {
			doSitemesh();
		} else {
			getTemplate(getLocation()).process(createModel(), getWriter());
		}
	}

	private void doSitemesh() throws IOException, TemplateException,
			TemplateModelException {
		Buffer buffer = new Buffer(getPageParser(), getEncoding());
		Template template = getTemplate(getLocation());
		template.process(createModel(), buffer.getWriter());
		Page page = buffer.parse();

		Decorator decorator = getDecorator(getRequest(), page);
		if (decorator != null && decorator.getPage() != null) {
			applyDecorator(page, decorator);
			return;
		}

		page.writePage(getWriter());
	}

	protected void applyDecorator(Page page, Decorator decorator) throws IOException, TemplateException {
		doRander(page, decorator, getWriter());
	}

	protected void doRander(Page page, Decorator decorator, Writer writer)
			throws IOException, TemplateException {
		Template template = getTemplate(decorator.getPage());
		SimpleHash model = createModel();
		model.put("title", page.getTitle());

		StringWriter body = new StringWriter();
		page.writeBody(body);
		model.put("body", body.toString());

		if (page instanceof HTMLPage) {
			HTMLPage htmlPage = (HTMLPage) page;
			StringWriter head = new StringWriter();
			htmlPage.writeHead(head);
			model.put("head", head.toString());
		}

		model.put("page", page);
		template.process(model, writer);
	}

	Decorator getDecorator(HttpServletRequest request, Page page) {
		return getFactory().getDecoratorMapper().getDecorator(request, page);
	}

	public Factory getFactory() {
		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}

	public PageParser getPageParser() {
		return pageParser;
	}

	public void setPageParser(PageParser pageParser) {
		this.pageParser = pageParser;
	}
}