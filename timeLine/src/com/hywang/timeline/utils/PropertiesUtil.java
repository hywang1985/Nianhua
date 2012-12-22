package com.hywang.timeline.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	
	private static Properties configs;
	
	private static Logger logger = Logger.getLogger(PropertiesUtil.class);
	static {
		configs =new Properties();
		InputStream in = null;
		try {
			in = PropertiesUtil.class.getClassLoader().getResourceAsStream("com/hywang/timeline/development/development.properties");
			configs.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
	}

	public static Properties getProperties() {
		return configs;
	}
	public static String getProperty(String key) {
		return configs.getProperty(key);
	}

}
