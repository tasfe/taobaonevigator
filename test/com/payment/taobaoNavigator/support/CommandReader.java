package com.payment.taobaoNavigator.support;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class CommandReader {

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
		
		StringBuffer dataFilePath = new StringBuffer();
		dataFilePath.append(DATA_FILE_DIR).append(className)
					.append(DIR_SEPERATOR).append(methodName).append(DATA_FILE_SUFFIX);
		String path = dataFilePath.toString();
		
		List<String> lines = null;
		try {
			FileInputStream fstream = new FileInputStream(path);
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
			System.err.println("Error: " + e.getMessage());
		}
		
		return lines;

	}

}
