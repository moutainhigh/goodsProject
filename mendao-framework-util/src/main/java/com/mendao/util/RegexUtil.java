package com.mendao.util;

import java.util.regex.Pattern;

public class RegexUtil {

	public static final String EMAIL_FORMAT = "^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$"; //"/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/";"
	
	public static final String MOBILE_FORMAT = "^((13[0-9])|(15[^4,\\D])|(18[^4,\\D]))\\d{8}$";
	
	public static final String PHONE_FORMAT = "";
	
	public static final String PASSWORD_FORMAT = "^([A-Za-z0-9]{6,12})$";
	
	public static final String MONEY_FORMAT = "^([0-9]+([.]{1}[0-9]{1,2}){0,1})$";
	
	/**
	 * 检查输入字符串是否为电子邮箱
	 * @param input
	 * @return
	 */
	public static boolean isEmail(String input){
		return Pattern.matches(EMAIL_FORMAT, input);
	}
	
	public static boolean isMobile(String input){
		return Pattern.matches(MOBILE_FORMAT, input);
	}
	
	public static boolean matchPassword(String input){
		return Pattern.matches(PASSWORD_FORMAT, input);
	}
	
	public static boolean matchMoney(String input){
		return Pattern.matches(MONEY_FORMAT, input);
	}
}
