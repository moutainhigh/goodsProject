package com.mendao.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.mendao.common.util.StringUtil;

import net.spy.memcached.MemcachedClient;


public class CacheUtil {
	private static MemcachedClient memClent; 
	
	private static boolean hasMemcached = false;
	private static long latestCheck;
	private static long interval = 1000;
	private static Map<String, Object[]> objMap;
	private static final String MEMCACHED_HOST_KEY = "service.mamcached.host";

	static{
		try {
			String host = PropertiesUtil.getProperty(MEMCACHED_HOST_KEY);
			if(!StringUtil.isBlank(host)){
				memClent = new MemcachedClient(new InetSocketAddress(host, 11211));
				hasMemcached = true;
			}else{
				latestCheck = System.currentTimeMillis();
				objMap = new HashMap<String, Object[]>();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Object getValue(String key){
		if(hasMemcached){
			return memClent.get(key);
		}else{
			return getStaticValue(key);
		}
	}

    public static void setValue(String key, int exp, Object value) {    
    	if(hasMemcached){
    		memClent.set(key, exp, value);
    	}else{
    		setStaticValue(key, exp, value);
    	}
    } 
    
   /**
    * 
    * @param key
    * @param exp
    * @param value
    */
	private static synchronized void setStaticValue(String key, int exp, Object value) {
		Object[] mapVal = new Object[3];
		mapVal[0] = System.currentTimeMillis();
		mapVal[1] =  (interval * exp);
		mapVal[2] = value;
		objMap.put(key, mapVal);
		
		long curTime = System.currentTimeMillis();
		if ((curTime - latestCheck) > (interval * exp)) {
			for (String k : objMap.keySet()) {
				long time = Long.valueOf(objMap.get(k)[0].toString());
				time = curTime - time;
				if (time > (interval * exp)) {
					objMap.remove(k);
				}
			}

			latestCheck = System.currentTimeMillis();
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	private static synchronized Object getStaticValue(String key) {
		if (objMap.containsKey(key)) {
			long curTime = System.currentTimeMillis();
			long time = Long.valueOf(objMap.get(key)[0].toString());
			long expInterval = Long.valueOf(objMap.get(key)[1].toString());
			time = curTime - time;
			if (time > expInterval) {
				objMap.remove(key);
				return null;
			}else{
				return objMap.get(key)[2];
			}
		}else{
			return null;
		}
	}
	
	protected CacheUtil(){}
}
