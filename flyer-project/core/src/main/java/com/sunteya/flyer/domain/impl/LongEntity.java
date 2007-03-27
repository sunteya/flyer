/**
 * Created on 2006-9-5
 * Created by Sunteya
 */
package com.sunteya.flyer.domain.impl;

import java.io.Serializable;


/**
 *
 * @author 诸南敏
 * @email zhunm@dep5.com
 */
public abstract class LongEntity extends AbstractEntity implements Serializable {

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Serializable getIdentityCode() {
		return getId();
	}

}
