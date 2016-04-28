package com.mendao.framework.enums;

/**
 * 
 * @author libra
 *
 */
public enum UserTypeEnum {

	
	
	;
	private UserTypeEnum(Integer code, String display) {
		this.code = code;
		this.display = display;
	}
	
	private Integer code;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}

	private String display;
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
}
