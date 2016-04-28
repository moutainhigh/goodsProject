package com.mendao.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class PropertiesUtil {

	private static ConcurrentHashMap<String, String> configurations = new ConcurrentHashMap<String, String>();
	
	private static final org.apache.log4j.Logger LOG = Logger.getLogger(PropertiesUtil.class);
	
	/**
	 * 获取配置信息(默认配置文件为classpath:business.properties)
	 * @param key
	 * @return
	 */
	public static String getProperty(String key){
		/*
		if(!configurations.containsKey(key)){
			InputStream in = PropertiesUtil.class.getResourceAsStream("/business.properties");
			Properties prop = new Properties();
			try {
				prop.load(in);
			} catch (IOException e) {
				LOG.info(" *** properties error ***",e);
			}
			
			try{
				configurations.put(key, prop.getProperty(key));
			}finally{
				System.err.println("aaaaasssssssssss");
			}
		}

		return configurations.get(key);
		*/
		
		InputStream in = PropertiesUtil.class.getResourceAsStream("/business.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			LOG.info(" *** properties error ***",e);
		}
		
		try{
			return prop.getProperty(key);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 获取配置信息
	 * @param key：配置名称
	 * @param fileName：配置文件名称
	 * @return
	 */
	public static String getProperty(String key, String fileName){
		InputStream in = PropertiesUtil.class.getResourceAsStream("/" + fileName + ".properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			LOG.info(" *** properties error ***",e);
		}
		return prop.getProperty(key);
		
	}
}
