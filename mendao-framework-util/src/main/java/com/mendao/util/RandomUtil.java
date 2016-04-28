package com.mendao.util;

import java.util.Random;

import com.mendao.common.util.StringUtil;

public class RandomUtil {

	private static final String NUMBER_SOURCES = "0123456789";
	
	public static String randomNumber(int len){
		return randomNumber(len, NUMBER_SOURCES);
	}
	public static String randomNumber(int len, String sources){  
		if(StringUtil.isBlank(sources)){
			sources = NUMBER_SOURCES;
		}
        int codesLen = sources.length();  
        Random rand = new Random(System.currentTimeMillis());  
        StringBuilder verifyCode = new StringBuilder(len);  
        for(int i = 0; i < len; i++){  
            verifyCode.append(sources.charAt(rand.nextInt(codesLen-1)));  
        }  
        return verifyCode.toString();  
    }
	
	
	
	public static void main(String[] args){
		System.out.println(randomNumber(20));
		System.out.println(randomNumber(4));
	}
}
