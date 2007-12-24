/**
 * Created on 2007-2-8
 * Created by Sunteya
 */
package com.sunteya.flyer.domain;

import java.io.Serializable;



/**
 * @author Sunteya
 *
 */
public abstract class AbstractEntity implements Entity {

	public abstract Serializable getIdentityCode();

	private Integer lockVersion = 0;

	public Integer getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(Integer lockVersion) {
		this.lockVersion = lockVersion;
	}

	@Override
	public int hashCode() {
		if(isTransient()) {
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

		if (!(obj instanceof Entity)) {
			return false;
		}

		final Entity other = (Entity) obj;

		if (isTransient() && other.isTransient()) {
			return equalsTransient();
		}

		if (!isTransient()) {
			return getIdentityCode().equals(other.getIdentityCode());
		} else {
			return false;
		}
	}

	public boolean isTransient() {
		return getIdentityCode() == null;
	}

	public boolean isPersistent() {
		return !isTransient();
	}

	protected int hashCodeTransient() {
		return 0;
	}

	protected boolean equalsTransient() {
		return false;
	}
}
