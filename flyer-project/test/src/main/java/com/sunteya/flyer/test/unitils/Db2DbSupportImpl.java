/**
 * Created on 2007-6-11
 * Created by Sunteya
 */
package com.sunteya.flyer.test.unitils;

import static org.unitils.core.util.SQLUtils.*;
import static org.unitils.thirdparty.org.apache.commons.dbutils.DbUtils.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Set;

import javax.sql.DataSource;

import org.unitils.core.UnitilsException;
import org.unitils.core.dbsupport.Db2DbSupport;

/**
 * @author Sunteya
 *
 */
public class Db2DbSupportImpl extends Db2DbSupport {

	@Override
	public Set<String> getTableNames() {
		return getItemsAsStringSet("SELECT tabname FROM sysstat.tables WHERE UPPER(tabschema)='" + getSchemaName().toUpperCase() + "'", getDataSource());
	}

	@Override
	public boolean supportsSequences() {
		return true;
	}

    @Override
    public Set<String> getSequenceNames() {
        return getItemsAsStringSet("SELECT seqname FROM sysibm.syssequences WHERE UPPER(seqschema) = '" + getSchemaName().toUpperCase() + "' AND seqtype='S'", getDataSource());
    }

    public Set<String> getTableConstraintNames(String tableName) {
        return getItemsAsStringSet("SELECT CONSTNAME FROM syscat.tabconst WHERE UPPER(tabname) = '" + tableName + "' AND enforced='Y' AND type<>'P'", getDataSource());
    }

    @Override
    public void incrementSequenceToValue(String sequenceName, long newSequenceValue) {
        executeUpdate("ALTER SEQUENCE " + qualified(sequenceName) + " RESTART WITH " + newSequenceValue, getDataSource());
        getItemAsLong("SELECT NEXTVAL FOR " + qualified(sequenceName) + " FROM sysibm.sysdummy1", getDataSource());
    }

    @Override
    public void incrementIdentityColumnToValue(String tableName, String primaryKeyColumnName, long identityValue) {
    	if(exist("SELECT colname FROM syscat.columns WHERE UPPER(tabschema)='" + getSchemaName().toUpperCase() + "'" +
    			" AND UPPER(tabname)='" + tableName.toUpperCase() + "'" +
    			" AND UPPER(colname)='" + primaryKeyColumnName.toUpperCase() + "'" +
    			" AND identity='Y'", getDataSource())) {
    		executeUpdate("ALTER TABLE " + qualified(tableName) + " ALTER COLUMN " + primaryKeyColumnName + " RESTART WITH " + identityValue, getDataSource());
    	}
    }

    @Override
    public long getCurrentValueOfSequence(String sequenceName) {
        return getItemAsLong("SELECT NEXTVAL FOR " + qualified(sequenceName) + " FROM sysibm.sysdummy1", getDataSource());
    }

    public static boolean exist(String sql, DataSource dataSource) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            return resultSet.next();
        } catch (Exception e) {
            throw new UnitilsException("Error while executing statement: " + sql, e);
        } finally {
            closeQuietly(connection, statement, resultSet);
        }
    }
}
