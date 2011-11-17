package com.payment.taobaoNavigator.support;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

public final class DBInitializer {
	
	public static final Logger logger = Logger.getLogger(DBInitializer.class);
	
	/**
	 * initialize database for dao's unit test.
	 * 
	 * @param className class name which is being tested.
	 * @param methodName method name which will be tested.
	 */
	public static void doInit(String className, String methodName) {
		
		if (className == null || className.isEmpty()) {
			return;
		}
		
		if (methodName == null || methodName.isEmpty()) {
			return;
		}
		
		try {
			Connection conn = null;
			Statement stmt = null;
			List<String> commands = CommandReader.read(className, methodName);
			//get connection only if commands exist.
			if (commands != null && !commands.isEmpty()) {
				conn = ConnectionFactory.getConnection();
				if (conn == null) {
					logger.error("database initialization ERROR can not get connection by current configuration.");
					return;
				}
				stmt = conn.createStatement();
				for (String command : commands) {
					logger.debug("database initialization command: " + command);
					stmt.addBatch(command);
				}
			}
			//execute
			int[] counts = stmt.executeBatch();
			
			//clear resource
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}

			logger.debug("database initialization done successfully.");
			
			//...
		} catch (SQLException e) {
			logger.error(e);
		}

	}
}
