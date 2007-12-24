/**
 * Created on 2007-11-28
 * Created by Sunteya
 */
package com.sunteya.flyer.validation;

import com.sunteya.flyer.i18n.MessageCode;

/**
 * @author Sunteya
 *
 */
public interface PathMessageCodeResolver {

	MessageCode resolvePathMessageCode(String path);

}