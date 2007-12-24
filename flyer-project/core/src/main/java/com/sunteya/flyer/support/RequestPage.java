/**
 * Created on 2007-7-5
 * Created by Sunteya
 */
package com.sunteya.flyer.support;

import java.io.Serializable;

/**
 * @author Sunteya
 *
 */
public interface RequestPage extends Serializable {

	int getRequestPageIndex();

	int getRequestPageSize();

}