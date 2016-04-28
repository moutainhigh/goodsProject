/**
 * 91mydoor.com Inc.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.mendao.framework.base.jpa;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.mendao.common.util.ArrayUtil;
import com.mendao.common.util.StringUtil;
/**
 * Get/Post请求参数工具类
 * 
 * @author HarrisonHan
 * @version $Id: ParamsUtil.java, v 0.1 2015年6月25日 下午1:27:37 HarrisonHan Exp $
 */
public class ParamsUtil {

    public static final String   QUERY       = "q_";
    /**
     * 不需要记录在 PageEntity 的Map 集合中的
     */
    public static final String[] STORE_BLACK_FIELD = { "curpage", "pagesize", "totalpage" };

    public static final String   SPLIT       = "&";

    /**
     * 根据请求获取参数集合并进行封装，构建PageEntity对象
     * 
     * @param request 请求
     * @return 分页查询实体
     */
    public static PageEntity createPageEntityFromRequest(final HttpServletRequest request,
                                                         int pageSize) {
        Enumeration<String> parameterNames = request.getParameterNames();
        PageEntity myPage = new PageEntity();
        myPage.setPagesize(pageSize);
        myPage.setPageUrl(request.getRequestURL().toString());
        myPage.setParams(new HashMap<String, Object>());
        while (parameterNames.hasMoreElements()) {
            String name = StringUtil.trim(parameterNames.nextElement().toString());
            if (StringUtil.isNotBlank(name)) {
                if (name.startsWith(QUERY)) {
                    String fieldName = name.substring(2);
                    if (StringUtil.isNotBlank(fieldName)
                        && !ArrayUtil.contains(STORE_BLACK_FIELD, fieldName)) {
                        String value = StringUtil.trim(request.getParameter(name));
                        if (StringUtil.isNotBlank(value)) {
                        	myPage.getParams().put(fieldName, value);
                        }
                    }
                }
            }
        }
        String curpage = StringUtil.trim(request.getParameter(STORE_BLACK_FIELD[0]));
        String pagesize = StringUtil.trim(request.getParameter(STORE_BLACK_FIELD[1]));
        String totalpage = StringUtil.trim(request.getParameter(STORE_BLACK_FIELD[2]));
        if (StringUtil.isNotBlank(curpage)) {
        	myPage.setCurrentpage(Integer.parseInt(curpage));
        } else {
        	myPage.setCurrentpage(1);
        }
        if (StringUtil.isNotBlank(pagesize)) {
        	myPage.setPagesize(Integer.parseInt(pagesize));
        }else{
        	//myPage.setPagesize(20);
        }
        if (StringUtil.isNotBlank(totalpage)) {
        	myPage.setTotalpage(Integer.parseInt(totalpage));
        }
        return myPage;
    }
    /**
     * 页面条件
     * auto zhaolei
     * @param model
     * @param myPage
     */
    public static void addAttributeModle(Model model, PageEntity myPage){
    	  if(myPage.getParams() != null){
    		   Set<String> keys =  myPage.getParams().keySet();
    		   Iterator it = keys.iterator();
    		   String key = "" ;
    		   while(it.hasNext()){
    			   key = (String)it.next();
    			   model.addAttribute(key.replace(".","_"), myPage.getParams().get(key));
    		   }
    	  }
    }
}
