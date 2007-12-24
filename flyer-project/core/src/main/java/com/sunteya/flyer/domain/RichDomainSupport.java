/**
 * Created on 2007-8-30
 * Created by Sunteya
 */
package com.sunteya.flyer.domain;


/**
 * @author Sunteya
 *
 */
public abstract class RichDomainSupport extends LongEntity {

	public void save() {
		onSave();
		doSave();
	}
	protected void onSave() { }
	protected abstract void doSave();

	public void remove() {
		onRemove();
		doRemove();
	}
	protected void onRemove() { }
	protected abstract void doRemove();

	public void update() {
		onUpdate();
		doUpdate();
	}
	protected void onUpdate() { }
	protected abstract void doUpdate();

	public final void saveOrUpdate() {
		if(getIdentityCode() == null) {
			save();
		} else {
			update();
		}
	}
}