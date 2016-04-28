package com.mendao.remote.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;



public class RemoteUtil {

	/**
     * 日志
     */
    private static final Logger logger = Logger.getLogger(RemoteUtil.class);

//    /**
//     * 远程调用课程信息接口服务，并返回json结果
//     * 
//     * @param serviceEnum 服务枚举
//     * @param param 课程id参数
//     * @return
//     * @throws BusinessCheckException
//     */
//    public String getJsonResult(RemoteServiceEnum serviceEnum, PageEntity pageEntity) throws BusinessCheckException {
//        String serviceUrl = serviceProperties.getDomain() + serviceEnum.getValue();
//        String paramStr = this.getRequestParams(pageEntity);
//        if (StringUtil.isNotBlank(paramStr)) {
//            serviceUrl = serviceUrl + "?" + paramStr;
//        }
//        if (StringUtil.isBlank(serviceUrl)) {
//        	logger.error("The Json service url is not init");
//            throw new BusinessCheckException("Remote service url is null.");
//        }
//        String jsonString = null;
//        try {
//            jsonString = loadJson(serviceUrl);
//        } catch (Exception e) {
//            LOG.warn("Synchronization Failure:" + e.getMessage());
//            throw new BusinessCheckException(
//                "Synchronization Failure,Please contact administrator." + e.getMessage());
//        }
//        return jsonString;
//    }
//    
    
    /**
     * 远程调用
     * 
     * @param url 远程地址
     * @param method 调用类型（GET、POST、PUT、UPDATE、DELETE）
     * @param params
     * @return json
     */
    public static String getJsonResult(String url, String method, @SuppressWarnings("rawtypes") Map params) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            HttpURLConnection uc = (HttpURLConnection) urlObject.openConnection();
            uc.setRequestMethod("POST");
            uc.setConnectTimeout(6000);
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            logger.error(e);
            //throw new BusinessRuntimeException(e);
        } catch (IOException e) {
            logger.error(e);
            //throw new Exception(e);
        }
        return json.toString();
    }
    
    /**
     * 远程调用（Get类型API）
     * 
     * @param url
     * @param params
     * @return json
     */
    public static String getJsonResult(String url, @SuppressWarnings("rawtypes") Map params) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            HttpURLConnection uc = (HttpURLConnection) urlObject.openConnection();
            uc.setRequestMethod("GET");
            uc.setConnectTimeout(6000);
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            logger.error(e);
            //throw new BusinessRuntimeException(e);
        } catch (IOException e) {
            logger.error(e);
            //throw new BusinessRuntimeException(e);
        }
        return json.toString();
    }
}
