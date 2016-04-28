/**
 * 91mydoor.com Inc.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.mendao.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 移动站工具类
 * 
 * @author HarrisonHan
 * @version $Id: MobileUtil.java, v 0.1 2015年7月1日 上午9:25:56 HarrisonHan Exp $
 */
public class MobileUtil {

    static String  phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
                              + "|windows (phone|ce)|blackberry"
                              + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
                              + "|laystation portable)|nokia|fennec|htc[-_]"
                              + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    static String  tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
                              + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    //移动设备正则匹配：手机端、平板  
    static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
    static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

    /** 
     * 检测是否是移动设备访问 
     */
    public static boolean check(String userAgent) {
        if (null == userAgent) {
            userAgent = "";
        }
        // 匹配    
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        if (matcherPhone.find() || matcherTable.find()) {
            return true;
        } else {
            return false;
        }
    }
}
