package com.walmart.fraudengine.web.util;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.walmart.fraudengine.exception.ErrorCode;
import com.walmart.fraudengine.exception.FraudEngineSystemException;
import com.walmart.fraudengine.util.FraudCheckConst;
import com.walmart.fraudengine.util.PropertiesUtil;

/**
 * 
 * @author rxding
 *
 */
public class ConnectivityDetector {
	
	/**
	 * 
	 * @return
	 * @throws FraudEngineSystemException
	 */
	public static boolean isDatabaseAvailable() throws FraudEngineSystemException {
		
		boolean returnVal = false;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource)ic.lookup(FraudCheckConst.JNDI_DATASOURCE);          
			java.sql.Connection conn = ds.getConnection();       
			conn.close();
			
			returnVal = true;
			
		} catch (Exception e) {
			throw new FraudEngineSystemException(ErrorCode.UNCATEGORIZED_SYSTEM_EXCEPTION, e);
		}
		
		return returnVal;
	}

	/**
	 * 
	 * @return
	 * @throws FraudEngineSystemException
	 */
	public static boolean isQueueAvailable() throws FraudEngineSystemException {
	
		boolean returnVal = false;
		
		try {
			/* Protocal: TCP
			// get connection parameters
			String queueServerUrl = PropertiesUtil.getAppProperty(FraudCheckConst.KEY_TIBCO_URL_TCP);
			String queueUserName  = "";//PropertiesUtil.getAppProperty(FraudCheckConst.KEY_TIBCO_USER)
			String queuePassword  = "";//PropertiesUtil.getAppProperty(FraudCheckConst.KEY_TIBCO_PASSWORD)
			
			QueueConnectionFactory queueFactory = new TibjmsQueueConnectionFactory(queueServerUrl);
			QueueConnection queueConn = queueFactory.createQueueConnection(queueUserName, queuePassword);
			queueConn.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			queueConn.start();
			queueConn.close();
			
			returnVal = true;
			*/
			
			/* Protocal: tibjmsnaming */
			String contextFactory = "com.tibco.tibjms.naming.TibjmsInitialContextFactory";
			String providerUrl = PropertiesUtil.getAppProperty(FraudCheckConst.KEY_TIBCO_URL);
			String userName = "";
			String password = "";
			String connectionFactoryName = PropertiesUtil.getAppProperty(FraudCheckConst.KEY_TIBCO_CONNECTION_FACTORY);;
			Properties tibcoProperties = new Properties();
			tibcoProperties.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
			tibcoProperties.put(Context.PROVIDER_URL, providerUrl);
			tibcoProperties.put(Context.REFERRAL, "throw");
			tibcoProperties.put(Context.SECURITY_PRINCIPAL, userName);
			tibcoProperties.put(Context.SECURITY_CREDENTIALS, password);
			//create connection
			Context ctx = new InitialContext(tibcoProperties);
			ConnectionFactory factory = (ConnectionFactory)ctx.lookup(connectionFactoryName);
			javax.jms.Connection conn = factory.createConnection();
			conn.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			conn.close();
			
			returnVal = true;
			
		} catch (Exception e) {
			throw new FraudEngineSystemException(ErrorCode.UNCATEGORIZED_SYSTEM_EXCEPTION, e);
		}
		
		return returnVal;
	}

}
