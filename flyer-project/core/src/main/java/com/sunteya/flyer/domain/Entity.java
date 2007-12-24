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

	final String ID_PROPERTY_NAME = "id"; 
	
	Serializable getIdentityCode();

	Integer getLockVersion();

	boolean isTransient();

	boolean isPersistent();

	int hashCode();

	boolean equals(Object obj);

}