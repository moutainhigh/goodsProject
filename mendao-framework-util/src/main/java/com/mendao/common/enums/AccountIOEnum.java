package com.mendao.common.enums;

public enum AccountIOEnum {

	PAYMENT("PAYMENT", "付款"),
	PAYMENT_AMOUNT("PAYMENT_AMOUNT", "付款"),
	GATHERING("GATHERING", "收款"),
	GATHERING_AMOUNT("GATHERING_AMOUNT", "收款"),
	DRAWING_APPLY("DRAWING_APPLY", "提现中"),
	DRAWING_SUCCESS("DRAWING_SUCCESS", "提现成功"),
	DRAWING_CANCEL("DRAWING_CANCEL", "提现取消")
	;
	
	private AccountIOEnum(String code, String text){
		this.code = code;
		this.text = text;
	}
	
	private String code;
	private String text;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
