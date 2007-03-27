/**
 * Created on 2007-1-18
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate.usertype;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Sunteya
 *
 */
public class StringSetUserType extends StringCollectionUserType {

	public Class<?> returnedClass() {
		return Set.class;
	}

	@Override
	protected Collection<String> newCollection() {
		return new HashSet<String>();
	}
}