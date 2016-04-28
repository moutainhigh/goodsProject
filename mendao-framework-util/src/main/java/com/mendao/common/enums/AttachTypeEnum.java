package com.mendao.common.enums;

public enum AttachTypeEnum {

	IMAGE("IMAGE", "图片(JPG/JPEG/GIF/PNG)","gif,jpg,jpeg,png,bmp"),
	DOCUMENT("DOCUMENT", "文档(DOC/DOCX/EXCEL...)","doc,docx,xls,xlsx,ppt"),
	ZIPED("ZIPED", "压缩文件","zip,rar,gz,bz2"),
	MEDIA("MEDIA", "视频文件","swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb"),
	FLASH("FLASH", "flash视频文件","swf,flv"),
	file("FILE", "附件格式","doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2"),
	
	;
	private AttachTypeEnum(String code, String display, String value){
		this.code = code;
		this.display = display;
		this.value = value;
	}
	
	private String code;
	private String display;
	private String value;
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
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public static String getValue(String code){
		for(AttachTypeEnum at : AttachTypeEnum.values()){
			if(at.getCode().equals(code)){
				return at.getValue();
			}
		}
		
		return null;
	}
}
