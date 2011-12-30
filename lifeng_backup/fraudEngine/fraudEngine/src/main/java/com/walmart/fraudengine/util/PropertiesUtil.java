package com.walmart.fraudengine.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertiesUtil extends PropertyPlaceholderConfigurer {

	private static Properties appProperties;
	
	// to store properties construct by resource files;
	private static Map<String, Properties> propertiesMap;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, 
			Properties props) throws BeansException {
		//load properties
		super.processProperties(beanFactory, props);
		
		appProperties = props;
	}

	public static String getAppProperty(String key) {
		return appProperties.getProperty(key);
	}
	
	public static String getPropertyFromResource(String resourcePath, 
			String key) throws IOException {
		
		//if the properties have been initialized before.
		if (propertiesMap.containsKey(resourcePath)) {
			Properties properties = propertiesMap.get(resourcePath);
			if (properties != null) {
				return properties.getProperty(key);
			} else {
				return null;
			}
		}
		
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getResourceAsStream(resourcePath);
			Properties properties = new Properties();
			properties.load(is);
			
			//add new entry for Properties
			propertiesMap.put(resourcePath, properties);
			
			return properties.getProperty(key);

		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

}
