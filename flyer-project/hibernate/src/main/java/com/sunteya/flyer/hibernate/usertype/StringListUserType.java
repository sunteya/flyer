/**
 * Created on 2007-1-18
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate.usertype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Sunteya
 *
 */
public class StringListUserType extends StringCollectionUserType {

	public Class<?> returnedClass() {
		return List.class;
	}

	@Override
	protected Collection<String> newCollection() {
		return new ArrayList<String>();
	}
}