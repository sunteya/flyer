/**
 * Created on 2007-3-1
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

/**
 * @author Sunteya
 *
 */
public class NotFoundView implements View {

	public String getContentType() {
		return null;
	}

	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
}
