package com.walmart.fraudengine.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * support class for writing messages to output.
 * 
 * @author rxding
 *
 */
public class LogSupport{
	
	/**
	 * log the message with Level.INFO.
	 * 
	 * @param message		the message which will be logged.
	 */
	public static void info(String message){
		Logger logger = getLogger();
		if (logger.isEnabledFor(Level.INFO)){
			logger.info(message);
		}
	}
	
	/**
	 * log the message with Level.INFO.
	 * 
	 * @param message		the message which will be logged.
	 * @param t				the throwable object.
	 */
	public static void info(String message, Throwable t){
		Logger logger = getLogger();
		if (logger.isEnabledFor(Level.INFO)){
			logger.info(message,t);
		}
	}
	
	/**
	 * log the message with Level.DEBUG.
	 * 
	 * @param message		the message which will be logged.
	 */
	public static void debug(String message){
		Logger logger = getLogger();
		if (logger.isEnabledFor(Level.DEBUG)){
			logger.debug(message);
		}
	}
	
	/**
	 * log the message with Level.DEBUG.
	 * 
	 * @param message		the message which will be logged.
	 * @param t				the throwable object.
	 */
	public static void debug(String message, Throwable t){
		Logger logger = getLogger();
		if (logger.isEnabledFor(Level.DEBUG)){
			logger.debug(message, t);
		}
	}
	
	/**
	 * log the message with Level.WARN.
	 * 
	 * @param message		the message which will be logged.
	 */
	public static void warn(String message){
		Logger logger = getLogger();
		if (logger.isEnabledFor(Level.WARN)){
			logger.warn(message);
		}
	}
	
	/**
	 * log the message with Level.WARN.
	 * 
	 * @param message		the message which will be logged.
	 */
	public static void warn(String message, Throwable t){
		Logger logger = getLogger();
		if (logger.isEnabledFor(Level.WARN)){
			logger.warn(message, t);
		}
	}
	
	
	/**
	 * log the message with Level.ERROR.
	 * 
	 * @param message		the message which will be logged.
	 */
	public static void error(String message){
		Logger logger = getLogger();
		if (logger.isEnabledFor(Level.ERROR)){
			logger.error(message);
		}
	}
	
	/**
	 * log the message with Level.ERROR.
	 * 
	 * @param message		the message which will be logged.
	 * @param t				the throwable object.
	 */
	public static void error(String message, Throwable t){
		Logger logger = getLogger();
		if (logger.isEnabledFor(Level.ERROR)){
			logger.error(message, t);
		}
	}
	
	
	/**
	 * get the logger instant.
	 * 
	 * @return				the logger object.
	 */
	private static Logger getLogger(){
		StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
		if(traceElements==null||traceElements.length<=3)
			return Logger.getRootLogger();
		// this method --> calling method in this class --> caller class
		int caller_idx = 3;
		return Logger.getLogger(traceElements[caller_idx].getClassName());
	}
	
}
