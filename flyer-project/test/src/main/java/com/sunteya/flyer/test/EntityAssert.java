/**
 * Created on 2007-3-19
 * Created by Sunteya
 */
package com.sunteya.flyer.test;

import static junit.framework.Assert.*;
import static org.unitils.reflectionassert.ReflectionAssert.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import com.sunteya.flyer.domain.Entity;

/**
 * @author Sunteya
 * 
 */
public abstract class EntityAssert {
	private static String IDENTITY_PROP_NAME = "IdentityCode";

	public static void assertEntityId(Serializable id, Entity entity) {
		assertEquals(id, entity.getIdentityCode());
	}

	public static void assertEntityIds(Entity[] entities, Serializable... ids) {
		assertPropertyRefEquals(IDENTITY_PROP_NAME, Arrays.asList((Serializable[]) ids), Arrays.asList((Object[]) entities));
	}

	public static void assertEntityIds(Collection<? extends Entity> entities, Serializable... ids) {
		assertPropertyRefEquals(IDENTITY_PROP_NAME, Arrays.asList((Serializable[]) ids), entities);
	}
}
