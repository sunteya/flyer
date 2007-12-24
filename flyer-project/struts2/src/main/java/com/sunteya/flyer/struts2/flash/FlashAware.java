/**
 * Created on 2007-6-21
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.flash;

import java.util.Map;

/**
 * @author Sunteya
 *
 */
public interface FlashAware {
	void setFlash(Map<String, Object> flash);
}
