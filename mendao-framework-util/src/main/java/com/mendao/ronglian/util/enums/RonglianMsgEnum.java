package com.mendao.ronglian.util.enums;

import java.util.HashMap;
import java.util.Map;

import com.mendao.common.util.StringUtil;


public enum RonglianMsgEnum {
	
	REGISTER("ronglian.template.register", "注册时请求验证码"),
	FORGET_PWD("ronglian.template.forgetpwd", "忘记密码时请求验证码"),
	BIND_MOBILE("ronglian.template.bindmobile", "绑定手机号时的请求验证码")
	
	;
	private RonglianMsgEnum(String code, String display){
		this.code = code;
		this.display = display;
	}
	
	private String code;
	private String display;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	
	public static final Map<String, RonglianMsgEnum> getEnumMapByCode(){

		 Map<String,RonglianMsgEnum> enums = new HashMap<String, RonglianMsgEnum>();
		 enums.put(REGISTER.code, REGISTER);
		 enums.put(FORGET_PWD.code, FORGET_PWD);
		 enums.put(BIND_MOBILE.code, BIND_MOBILE);
		 
//		 System.out.println(REGISTER.code);
//		 System.out.println(FORGET_PWD.code);
//		 System.out.println(BIND_MOBILE.code);
		 return enums ;
	}
	
	
//	@Override
	public static RonglianMsgEnum convert(String code){
		String key = "ronglian.template." + code.toLowerCase();
//		System.out.println(key);
//		System.out.println(REGISTER.code);
//		System.out.println(StringUtil.equals(key, REGISTER.code));
		return getEnumMapByCode().get(key);
	}
	
	
	public static void main(String[] args){
		RonglianMsgEnum t = Enum.valueOf(RonglianMsgEnum.class, "");
		System.out.println(t.getDisplay());
	}
}
