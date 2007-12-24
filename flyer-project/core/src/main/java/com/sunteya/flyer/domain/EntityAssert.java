/**
 * Created on 2007-3-19
 * Created by Sunteya
 */
package com.sunteya.flyer.domain;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.sunteya.flyer.support.Pagination;


/**
 * @author Sunteya
 *
 */
public abstract class EntityAssert {

	public static void assertEntityId(Serializable id, Entity entity) {
		assertEquals(id, entity.getIdentityCode());
	}

	public static void assertEntityIds(Entity[] entities, Serializable... ids) {
		assertEntityIds(Arrays.asList(entities), ids);
	}

	public static void assertEntityIds(Pagination<? extends Entity> pagination, Serializable... ids) {
		assertEntityIds(pagination.getElements(), ids);
	}

	public static void assertEntityIds(Collection<? extends Entity> entities, Serializable... ids) {
		List<Serializable> entityIds = new ArrayList<Serializable>();
		for (Entity entity : entities) {
			entityIds.add(entity.getIdentityCode());
		}
		assertEquals(Arrays.asList(ids), entityIds);
	}
}
