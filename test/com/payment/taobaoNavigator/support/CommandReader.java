package com.payment.taobaoNavigator.support;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.transaction.xa.commands.Command;

import org.apache.log4j.Logger;

public final class CommandReader {

	public static final Logger logger = Logger.getLogger(Command.class);
	
	//TODO put in properties
	public static final String DATA_FILE_DIR = "D:\\TaobaoNevigator\\workspace" +
			"\\taobaonavigator_online\\test\\com\\payment\\taobaoNavigator\\data\\";
	public static final String DATA_FILE_SUFFIX = ".sql";
	public static final String DIR_SEPERATOR = "\\";
	
	/**
	 * TODO add specification
	 * 
	 * @param className
	 * @param methodName
	 * 
	 * @return list of sql commands
	 */
	public static List<String> read(String className, String methodName) {
		
		List<String> lines = new ArrayList<String>();
		
		StringBuffer dataFilePath = new StringBuffer();
		dataFilePath.append(DATA_FILE_DIR).append(className)
					.append(DIR_SEPERATOR).append(methodName).append(DATA_FILE_SUFFIX);
		String path = dataFilePath.toString();
		File file = new File(path);
		if (!file.exists()){
			logger.warn("Warn: " + path + " does not exist. ignore the initialization process.");
			return lines;
		}
	
		try {
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				if (lines == null) {
					lines = new ArrayList<String>();
				}
				lines.add(strLine);
			}
			in.close();
			
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
		}
		
		return lines;

	}

}
