/**
 * Created on 2007-2-8
 * Created by Sunteya
 */
package com.sunteya.flyer.domain.impl;

import java.io.Serializable;

/**
 * @author Sunteya
 *
 */
public abstract class StringEntity extends AbstractEntity {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Serializable getIdentityCode() {
		return getId();
	}

}
