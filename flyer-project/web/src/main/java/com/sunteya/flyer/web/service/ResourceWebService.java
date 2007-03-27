/**
 * Created on 2007-1-25
 * Created by Sunteya
 */
package com.sunteya.flyer.web.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;


/**
 * @author Sunteya
 *
 */
public class ResourceWebService {

	private ResourceProvider provider;

	public void service(String resourceId, boolean attachment, HttpServletRequest request, HttpServletResponse response) throws IOException {

		UploadResource resource = findUploadResource(resourceId);

		if (resource == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String originalFilename = resource.getOriginalFilename();
		response.setContentType(getContentType(originalFilename, request));

		if (attachment) {
			response.addHeader("Content-Disposition", "attachment; filename="
					+ originalFilename);
		} else {
			response.addHeader("Content-Disposition", "inline; filename="
					+ originalFilename);
		}

		InputStream in = resource.getInputStream();
		OutputStream out = response.getOutputStream();
		IOUtils.copy(in, out);
		IOUtils.closeQuietly(in);
		IOUtils.closeQuietly(out);
	}

	public long getLastModified(String resourceId) {
		UploadResource resource = findUploadResource(resourceId);
		if(resource == null) {
			return -1;
		}

		return resource.getLastModified();
	}

	private UploadResource findUploadResource(String resourceId) {
		if (resourceId == null) {
			return null;
		}

		UploadResource resource = provider.loadResourceByResourceId(resourceId);
		return resource;
	}



	private String getContentType(String fileName, HttpServletRequest request) {
		ServletContext context = getServletContext(request);
		return context.getMimeType(fileName);
	}

	protected ServletContext getServletContext(HttpServletRequest request) {
		return request.getSession(true).getServletContext();
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public void setResourceProvider(ResourceProvider service) {
		this.provider = service;
	}
}
