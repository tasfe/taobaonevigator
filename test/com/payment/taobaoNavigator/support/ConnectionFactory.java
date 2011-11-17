package com.payment.taobaoNavigator.support;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.payment.taobaoNavigator.constant.GlobalConstants;

public final class ConnectionFactory {

	public static final Logger logger = Logger.getLogger(ConnectionFactory.class);

	private static final Properties props = loadDatabaseProps();
	
	private static Properties loadDatabaseProps() {
		Properties props = new Properties();
        try {
        	InputStream in = ConnectionFactory.class.getResourceAsStream(GlobalConstants.DB_CONFIG_PATH);
        	props.load(in);	
        } catch (IOException e) {
        	logger.error("ERROR:could not load properties for database initialization.", e);
        }
        return props;
	}
	
	private ConnectionFactory() {
		try {
			Class.forName(GlobalConstants.DB_DRIVER);
		} catch (ClassNotFoundException e) {
			logger.error("ERROR:exception loading driver class", e);
		}
	}

	public static Connection getConnection() throws SQLException {
		String url = props.getProperty(GlobalConstants.DB_URL);
		String username = props.getProperty(GlobalConstants.DB_USER);
		String password =props.getProperty(GlobalConstants.DB_PWD);
		return DriverManager.getConnection(url, username, password);
	}
}
