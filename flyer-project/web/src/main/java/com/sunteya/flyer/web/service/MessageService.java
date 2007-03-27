/**
 * Created on 2007-3-28
 * Created by Sunteya
 */
package com.sunteya.flyer.web.service;

import java.util.Map;

/**
 * @author Sunteya
 *
 */
public interface MessageService {

	String getMessage(Object key);

	String getMessage(Object key, String defaultMessage);

	String getMessage(Object key, Object[] args);

	String getMessage(Object key, Object[] args, String defaultMessage);

	String getMessage(Object key, Map<String, ?> params);

	String getMessage(Object key, Map<String, ?> params, String defaultMessage);

}