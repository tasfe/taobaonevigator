package com.walmart.fraudengine.processor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ConvertTestXmlToString {
	
	public static String convertTestXmltoString(String classPath)throws Exception{
		FileInputStream fis = new FileInputStream(classPath);
		BufferedReader br= new BufferedReader(new InputStreamReader(fis)); 
		StringBuffer sb = new StringBuffer();
		String tmp = null;
		while((tmp=br.readLine())!=null){
		sb.append(tmp);
		}
		String strXml = sb.toString();
		return strXml;
	}

}
