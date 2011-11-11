package com.payment.taobaoNavigator.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public final class ConnectionFactory {

	public static final Logger logger = Logger.getLogger(ConnectionFactory.class);
	
	//TODO put these information into properties
	private static final String DATABASE_URL = "jdbc:mysql://192.168.71.187:3306/taobaonavigator_test?useUnicode=true&characterEncoding=UTF-8";
	private static final String DATABASE_USER = "test";
	private static final String DATABASE_PASSWORD = "000000";
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	
	private ConnectionFactory() {
		try {
			Class.forName(DATABASE_DRIVER);
		} catch (ClassNotFoundException e) {
			logger.error("ERROR:exception loading driver class");
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);

	}

	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (Exception ignored) {
			
		}
	}

	public static void close(Statement stmt) {
		try {
			stmt.close();
		} catch (Exception ignored) {
			
		}
	}

	public static void close(Connection conn) {
		try {
			conn.close();
		} catch (Exception ignored) {
			
		}
	}
}
