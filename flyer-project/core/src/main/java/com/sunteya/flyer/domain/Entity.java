/**
 * Created on 2007-3-16
 * Created by Sunteya
 */
package com.sunteya.flyer.domain;

import java.io.Serializable;

/**
 * @author Sunteya
 *
 */
public interface Entity extends Serializable {

	Serializable getIdentityCode();

	int hashCode();

	boolean equals(Object obj);

}