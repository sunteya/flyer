/**
 * Created on 2006-9-20
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import com.sunteya.commons.util.GenericUtils;

/**
 *
 * @author 诸南敏
 * @email zhunm@dep5.com
 */
public abstract class AbstractGenericCommandController<Cmd> extends
		AbstractCommandController {

	public AbstractGenericCommandController() {
		setCommandClass(GenericUtils.getSuperClassTypeArgument(this, 0));
	}

	public AbstractGenericCommandController(Class<Cmd> commandClass) {
		setCommandClass(commandClass);
	}

	@SuppressWarnings("unchecked")
	protected Cmd castCommand(Object command) {
		return (Cmd) command;
	}

	private void throwUnhappenedException() {
		throw new RuntimeException("Unhappened!");
	}

	// =====================================================
	// onBind
	// -----------------------------------------------------
	@Override
	protected final void onBind(HttpServletRequest request, Object command,
			BindException errors) throws Exception {
		onCommandBind(request, castCommand(command), errors);
	}

	@Override
	protected final void onBind(HttpServletRequest request, Object command) throws Exception {
		throwUnhappenedException();
	}

	protected void onCommandBind(HttpServletRequest request, Cmd command,
			BindException errors) throws Exception {
		onCommandBind(request, command);
	}

	protected void onCommandBind(HttpServletRequest request, Cmd command)
			throws Exception {
	}


	// =====================================================
	// onBindAndValidate
	// -----------------------------------------------------
	@Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		onCommandBindAndValidate(request, castCommand(command), errors);
	}

	protected void onCommandBindAndValidate(HttpServletRequest request, Cmd command,
			BindException errors) {
	}

	// =====================================================
	// handle
	// -----------------------------------------------------
	@Override
	protected final ModelAndView handle(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		return handleCommand(request, response, castCommand(command), errors);
	}

	protected abstract ModelAndView handleCommand(HttpServletRequest request,
			HttpServletResponse response, Cmd command, BindException errors)
			throws Exception;
}
