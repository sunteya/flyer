/**
 * Created on 2007-4-17
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractFormController;

import com.sunteya.commons.util.GenericUtils;

/**
 * @author Sunteya
 *
 */
public abstract class AbstractWizardContorller<Cmd> extends AbstractFormController {

	private String[] pages;
	private String successView;

	public AbstractWizardContorller() {
		super();
		init(GenericUtils.getSuperClassTypeArgument(this));
	}

	public AbstractWizardContorller(Class<?> commandClass) {
		super();
		init(commandClass);
	}

	private void init(Class<?> commandClass) {
		setSessionForm(true);
		setValidateOnBinding(false);
		setCommandClass(commandClass);
	}

	protected String getPageSessionAttributeName(HttpServletRequest request) {
		return getFormSessionAttributeName(request)+ ".PAGE";
	}

	// =====================================================
	// Get
	// -----------------------------------------------------
	@Override
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
		int index = getFormPageIndex(request);
		request.getSession().setAttribute(getPageSessionAttributeName(request), index);
		return showForm(request, errors, getPages()[index], null);
	}

	protected abstract int getFormPageIndex(HttpServletRequest request);

	// =====================================================
	// Post
	// -----------------------------------------------------
	@SuppressWarnings("unchecked")
	protected final void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
		onBindAndValidate(request, (Cmd) command, errors, getProcessPage(request));
	}

	protected void onBindAndValidate(HttpServletRequest request, Cmd command, BindException errors, int page) throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		int processPage = getProcessPage(request);
		if(processPage == (getPageCount() - 1)) {
			return validatePagesAndFinish(request, response, (Cmd) command, errors);
		}

		validatePage((Cmd) command, errors, processPage);
		postProcessPage(request, (Cmd) command, errors, processPage);

		if(errors.hasErrors()) {
			return showForm(request, errors, getPages()[processPage], null);
		}

		return processSubmit(request, response, (Cmd) command, errors, processPage);
	}

	protected abstract ModelAndView processSubmit(HttpServletRequest request, HttpServletResponse response, Cmd command, BindException errors, int processPage) throws Exception;

	protected int getProcessPage(HttpServletRequest request) {
		Integer page = (Integer) request.getSession().getAttribute(getPageSessionAttributeName(request));

		return page.intValue();
	}

	protected void postProcessPage(HttpServletRequest request, Cmd command, Errors errors, int page) throws Exception {
	}

	protected ModelAndView validatePagesAndFinish(HttpServletRequest request, HttpServletResponse response, Cmd command, BindException errors) throws Exception {
		if (!suppressValidation(request, command)) {
			for (int page = 0; page < getPageCount(); page++) {
				validatePage(command, errors, page);
				if (errors.hasErrors()) {
					return showForm(request, errors, getPages()[page], null);
				}
			}
		}

		return processFinish(request, response, command, errors);
	}

	protected abstract ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Cmd command, BindException errors);

	protected void validatePage(Cmd command, BindException errors, int page) {
	}

	protected int getPageCount() {
		return getPages().length;
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public void setPages(String[] pages) {
		this.pages = pages;
	}

	public String[] getPages() {
		return pages;
	}

	public String getSuccessView() {
		return successView;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}
}
