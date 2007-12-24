/**
 * Created on 2006-9-20
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.controller;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sunteya.commons.util.GenericUtils;

/**
 *
 * @author 诸南敏
 * @email zhunm@dep5.com
 */
public abstract class GenericSimpleFormController<Cmd> extends
		SimpleFormController {

	public GenericSimpleFormController() {
		setCommandClass(GenericUtils.getSuperClassTypeArgument(this, 0));
	}

	public GenericSimpleFormController(Class<Cmd> commandClass) {
		setCommandClass(commandClass);
	}

	@SuppressWarnings("unchecked")
	protected Cmd castCommand(Object command) {
		return (Cmd) command;
	}

	private void throwUnhappenedException() {
		throw new RuntimeException("no know exception");
	}

	// =====================================================
	// FormChange
	// -----------------------------------------------------
	@Override
	protected final void onFormChange(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		onGenericFormChange(request, response, castCommand(command), errors);
	}

	@Override
	protected final void onFormChange(HttpServletRequest request,
			HttpServletResponse response, Object command) throws Exception {
		throwUnhappenedException();
	}

	protected void onGenericFormChange(HttpServletRequest request,
			HttpServletResponse response, Cmd command, BindException errors)
			throws Exception {
		onGenericFormChange(request, response, command);
	}

	protected void onGenericFormChange(HttpServletRequest request,
			HttpServletResponse response, Cmd command) throws Exception {
	};

	// =====================================================
	// onSubmit
	// -----------------------------------------------------
	@Override
	protected final ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		return onGenericSubmit(request, response, castCommand(command), errors);
	}

	@Override
	protected final ModelAndView onSubmit(Object command, BindException errors)
			throws Exception {
		throwUnhappenedException();
		return null;
	}

	@Override
	protected final ModelAndView onSubmit(Object command) throws Exception {
		throwUnhappenedException();
		return null;
	}

	@Override
	protected final void doSubmitAction(Object command) throws Exception {
		throwUnhappenedException();
	}

	protected ModelAndView onGenericSubmit(HttpServletRequest request,
			HttpServletResponse response, Cmd command, BindException errors)
			throws Exception {
		return onGenericSubmit(command, errors);
	}

	protected ModelAndView onGenericSubmit(Cmd command, BindException errors)
			throws Exception {
		ModelAndView mv = onGenericSubmit(command);
		if (mv != null) {
			return mv;
		} else {
			if (getSuccessView() == null) {
				throw new ServletException("successView isn't set");
			}
			return new ModelAndView(getSuccessView(), errors.getModel());
		}
	}

	protected ModelAndView onGenericSubmit(Cmd command) throws Exception {
		onSubmitCommand(command);
		return null;
	}

	protected void onSubmitCommand(Cmd command) throws Exception {
	}

	// =====================================================
	// referenceData
	// -----------------------------------------------------
	@SuppressWarnings("unchecked")
	@Override
	protected final Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws Exception {
		return referenceGenericData(request, castCommand(command), errors);
	}

	@SuppressWarnings("unchecked")
	protected Map referenceGenericData(HttpServletRequest request,
			Cmd command, Errors errors) throws Exception {
		return referenceData(request);
	}

	// =====================================================
	// currentFormObject
	// -----------------------------------------------------
	@Override
	protected final Object currentFormObject(HttpServletRequest request,
			Object sessionFormObject) throws Exception {
		return currentGenericFormObject(request, castCommand(sessionFormObject));
	}

	protected Cmd currentGenericFormObject(HttpServletRequest request,
			Cmd sessionFormObject) throws Exception {
		return sessionFormObject;
	}

	// =====================================================
	// onBindOnNewForm
	// -----------------------------------------------------
	@Override
	protected final void onBindOnNewForm(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		onGenericBindOnNewForm(request, castCommand(command), errors);
	}

	@Override
	protected final void onBindOnNewForm(HttpServletRequest request,
			Object command) throws Exception {
		throwUnhappenedException();
	}

	protected void onGenericBindOnNewForm(HttpServletRequest request,
			Cmd command, BindException errors) throws Exception {
		onGenericBindOnNewForm(request, command);
	}

	protected void onGenericBindOnNewForm(HttpServletRequest request,
			Cmd command) throws Exception {
	}

	// =====================================================
	// onBind
	// -----------------------------------------------------
	@Override
	protected final void onBind(HttpServletRequest request, Object command,
			BindException errors) throws Exception {
		onGenericBind(request, castCommand(command), errors);
	}

	@Override
	protected final void onBind(HttpServletRequest request, Object command)
			throws Exception {
		throwUnhappenedException();
	}

	protected void onGenericBind(HttpServletRequest request, Cmd command,
			BindException errors) throws Exception {
		onGenericBind(request, command);
	}

	protected void onGenericBind(HttpServletRequest request, Cmd command)
			throws Exception {
	}

	// =====================================================
	// onBindAndValidate
	// -----------------------------------------------------
	@Override
	protected final void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		onGenericBindAndValidate(request, castCommand(command), errors);
	}

	protected void onGenericBindAndValidate(HttpServletRequest request,
			Cmd command, BindException errors) throws Exception {
	}

}
