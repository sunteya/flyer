/**
 * Created on 2007-3-19
 * Created by Sunteya
 */
package com.sunteya.flyer.test.unitils;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unitils.core.Module;
import org.unitils.core.TestListener;
import org.unitils.core.Unitils;
import org.unitils.database.DatabaseModule;

/**
 * @author Sunteya
 *
 */
public class UpdateSchemaModule implements Module {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(UpdateSchemaModule.class);


	private Properties configuration;

	public TestListener createTestListener() {
		return new UpdateSchemaListener();
	}

	public DataSource getDataSource() {
		return getDatabaseModule().getDataSource();
	}

	protected DatabaseModule getDatabaseModule() {
		return Unitils.getInstance().getModulesRepository().getModuleOfType(
				DatabaseModule.class);
	}

	public void init(Properties configuration) {
		this.configuration = configuration;
	}


	public void updateDatabaseSchema(Object testObject) {
		if (testObject.getClass().getAnnotation(UpdateDatabaseSchema.class) == null) {
			return;
		}

		logger.info("Updating database schema if needed.");
		DBUpdateOnlyMaintainer dbMaintainer = new DBUpdateOnlyMaintainer(configuration,	getDataSource());
		dbMaintainer.updateDatabase();
	}

	/**
	 * The {@link TestListener} for this module
	 */
	private class UpdateSchemaListener extends TestListener {

		@Override
		public void beforeTestSetUp(Object testObject) {
			updateDatabaseSchema(testObject);
		}
	}
}
