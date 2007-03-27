/**
 * Created on 2007-3-23
 * Created by Sunteya
 */
package com.sunteya.flyer.test.unitils;

import java.util.Properties;

import javax.sql.DataSource;

import org.unitils.dbmaintainer.DBMaintainer;

/**
 * @author Sunteya
 *
 */
public class DBUpdateOnlyMaintainer extends DBMaintainer {

	public DBUpdateOnlyMaintainer() {
		super();
	}

	public DBUpdateOnlyMaintainer(Properties configuration, DataSource dataSource) {
		super(configuration, dataSource);
	}

	@Override
	public void updateDatabase() {
		// Disable FK and not null constraints, if enabled
		if (constraintsDisabler != null) {
			constraintsDisabler.disableConstraints();
		}
		// Update sequences to a sufficiently high value, if enabled
		if (sequenceUpdater != null) {
			sequenceUpdater.updateSequences();
		}
		// Generate a DTD to enable validation and completion in data xml files, if enabled
		if (dtdGenerator != null) {
			dtdGenerator.generateDtd();
		}
	}
}
