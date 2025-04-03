package com.echevarne.sap.cloud.facturacion.util;


import java.io.StringWriter;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;

import javax.sql.DataSource;

/**
 *
 * This class is an example of JNDI datasource provide within SCP
 *
 * @author Steven Ricardo Mendez Brenes
 * @since Jul 31 2019
 * @version 1.0
 *
 */
public class DBInformation
{
    String url = null;

    String dbName = null;
    int dbMajorVersion = 0;
    int dbMinorVersion = 0;

    String driverName = null;
    String driverVersion = null;

    String userName = null;


    public DBInformation(DataSource dataSource) throws SQLException
    {
        this(dataSource.getConnection().getMetaData());
    }

    public DBInformation(DatabaseMetaData metaData) throws SQLException
    {
        url = metaData.getURL();
        dbName = metaData.getDatabaseProductName();
        dbMajorVersion = metaData.getDatabaseMajorVersion();
        dbMinorVersion = metaData.getDatabaseMinorVersion();

        driverName = metaData.getDriverName();
        driverVersion = metaData.getDriverVersion();

        userName = metaData.getUserName();
    }

    public String getUrl()
    {
        return this.url;
    }

    public String getDbName()
    {
        return this.dbName;
    }

    public int getDbMajorVersion()
    {
        return this.dbMajorVersion;
    }

    public int getDbMinorVersion()
    {
        return this.dbMinorVersion;
    }

    public String getDriverName()
    {
        return this.driverName;
    }

    public String getDriverVersion()
    {
        return driverVersion;
    }

    public String getUserName()
    {
        return userName;
    }

    @Override
    public String toString()
    {
        final String CRLF = System.getProperty("line.separator");

        StringWriter str = new StringWriter();

        str.write(CRLF);
        str.write(MessageFormat.format("Driver Name: {0}{1}", this.driverName, CRLF));
        str.write(MessageFormat.format("Driver Version: {0}{1}", this.driverVersion, CRLF));
        str.write(MessageFormat.format("URL: {0}{1}", this.url, CRLF));
        str.write(MessageFormat.format("DB Name: {0}{1}", this.dbName, CRLF));
        str.write(MessageFormat.format("Username: {0}{1}", this.userName, CRLF));

        return str.toString();
    }
}
