/**
 * Created on 2007-2-8
 * Created by Sunteya
 */
package com.sunteya.flyer.domain.impl;

import java.io.Serializable;

import com.sunteya.flyer.domain.Entity;


/**
 * @author Sunteya
 *
 */
public abstract class AbstractEntity implements Entity {

	public abstract Serializable getIdentityCode();

	@Override
	public int hashCode() {
		if(getIdentityCode() == null) {
			return hashCodeTransient();
		}

		return getIdentityCode().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof LongEntity)) {
			return false;
		}

		final Entity other = (Entity) obj;

		if (getIdentityCode() == null && other.getIdentityCode() == null) {
			return equalsTransient();
		}

		if (getIdentityCode() != null) {
			return getIdentityCode().equals(other.getIdentityCode());
		} else {
			return false;
		}
	}

	protected int hashCodeTransient() {
		return 0;
	}

	protected boolean equalsTransient() {
		return false;
	}
}
