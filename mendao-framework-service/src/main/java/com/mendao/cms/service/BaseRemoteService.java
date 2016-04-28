package com.mendao.cms.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mendao.cms.enums.RemoteServiceEnum;
import com.mendao.exception.BusinessCheckException;
import com.mendao.util.PropertiesUtil;

/**
 * 
 * 远程json调用基础服务
 * 
 * @author HarrisonHan
 * @version $Id: BaseRemoteService.java, v 0.1 2015年6月27日 下午1:16:02 HarrisonHan Exp $
 */
public  class BaseRemoteService {
	/**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(BaseRemoteService.class);
    private static final String domain = PropertiesUtil.getProperty("service.domain");
    /**
	 * 发送post请求.
	 * 
	 * @param sendurl
	 *            请求URL
	 * @param data
	 *            数据
	 * @throws UnsupportedEncodingException 
     * @throws BusinessCheckException 
	 * */
	public  String doPost(RemoteServiceEnum serviceEnum, List <NameValuePair> nvps) throws UnsupportedEncodingException, BusinessCheckException {
		CloseableHttpClient client = HttpClients.createDefault();
		String url = domain + serviceEnum.getValue();
		HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		String responseContent = null; // 响应内容
		CloseableHttpResponse response = null;
		try {
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				responseContent = EntityUtils.toString(entity, HTTP.UTF_8);
			}
		} catch (Exception e) {
			LOG.debug("Connect the remote server error: " + e.getMessage());
			throw new BusinessCheckException("Connect the remote server error: " + e.getMessage());
		}  finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				LOG.debug("Close response error : " + e.getMessage());
				throw new BusinessCheckException("Close response error : " + e.getMessage());
			} finally {
				try {
					if (client != null)
						client.close();
				} catch (IOException e) {
					LOG.debug("Close client error : " + e.getMessage());
					throw new BusinessCheckException("Close client error : " + e.getMessage());
				}
			}
		}
		return responseContent;
	}
	
	/**
	 * 发送post请求.
	 * 
	 * @param sendurl
	 *            请求URL
	 * @throws BusinessCheckException 
	 * */
	public  String doGet(RemoteServiceEnum serviceEnum, String data) throws BusinessCheckException {
         	String url = domain + serviceEnum.getValue();
	        HttpClient client = new DefaultHttpClient();  
	        HttpGet get = new HttpGet(url + "?" + data);  
	        String responseContent = null; // 响应内容 
	        try {  
	            HttpResponse res = client.execute(get);  
	            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
	                HttpEntity entity = res.getEntity();  
	                responseContent = EntityUtils.toString(entity, HTTP.UTF_8);
	            }  
	        } catch (Exception e) {  
	        	LOG.debug("Connect the remote server error: " + e.getMessage());
	        	throw new BusinessCheckException("Connect the remote server error: " + e.getMessage());
	              
	        } finally{  
	            //关闭连接 ,释放资源  
	            client.getConnectionManager().shutdown();  
	        }  
	        return responseContent;  
	}
	/**
	 * 封装GET请求参数.
	 * @param params 参数Map
	 * @return 参数串
	 * */
	public  String buildParamsForGet(Map<String, Object> params) {
		StringBuffer paramStr = new StringBuffer("1=1");
		Iterator<String> it = params.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next(); 
			paramStr.append("&").append(key).append("=")
					.append(params.get(key));
		}
		return paramStr.toString();
	}
	
	public  List <NameValuePair> buildParamsForPost(Map<String, Object> params) {
		List <NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(params != null && !params.isEmpty()) {
			Iterator<String> it = params.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				nvps.add(new BasicNameValuePair(key, params.get(key) == null ? "" : params.get(key).toString()));
			}
		}
		return nvps;
	}
	
}
