/**
 * Created on 2007-1-25
 * Created by Sunteya
 */
package com.sunteya.flyer.web.service;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Sunteya
 *
 */
public interface UploadResource {
	String getOriginalFilename();

	InputStream getInputStream() throws FileNotFoundException;

	long getLastModified();
}
