package com.mendao.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mendao.common.util.StringUtil;
import com.mendao.util.MobileUtil;
import com.mendao.util.PropertiesUtil;

/**
 * 拦截器，获取用户上一次的访问URL
 * 
 * @author HarrisonHan
 * @version $Id: SiteHandlerInterceptor.java, v 0.1 2015年6月26日 上午10:17:16 HarrisonHan Exp $
 */
public class SiteHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final String INDEX  = "/index";

    private static final String SEARCH = "/search";

    private static final String LIST   = "/list";
    
    private static final String DOWNLOAD   = "/download";
    
    private static final String SERVICE_CDN = "service.cdn";

    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        String currentUrl = request.getServletPath();
        String referer = request.getHeader("Referer");
        String cdnServer = PropertiesUtil.getProperty(SERVICE_CDN);
        request.setAttribute("cdn", StringUtil.defaultIfBlank(cdnServer, ""));
        
        //判断访问设备
  		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
          if (null == userAgent) {
              userAgent = "";
          }
          boolean isFromMobile = MobileUtil.check(userAgent);
          //判断是否为移动端访问  
          if (!isFromMobile) {
        	  request.setAttribute("equipment", "pc");
          }else{
        	  request.setAttribute("equipment", "mobile");
          }
        
//        if(currentUrl.endsWith("/register")){
//	        Map<String, String[]> map = request.getParameterMap();
//	        for(String key : map.keySet()){
//	        	System.out.println(key);
//	        	request.setAttribute("mobile", map.get("mobile"));
//	        	request.setAttribute("nickName", map.get("nickName"));
//	        }
//        }
    }
}
