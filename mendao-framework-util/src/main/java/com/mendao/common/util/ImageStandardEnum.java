package com.mendao.common.util;

/**
 * 图片缩放比例
 * 
 * @author HarrisonHan
 *
 */
public enum ImageStandardEnum {

	/**
	 * 小图缩放比例
	 */
	MIN("min", 0.3, "min"),
	/**
	 * 中图缩放比例
	 */
	MID("mid", 0.5, "mid"),
	/**
	 * 大图缩放比例
	 */
	MAX("max", 0.8, "max");

	/**
	 * 规格类型
	 */
	public String type;
	/**
	 * 缩放比例
	 */
	public double ratio;

	public String folderName;

	ImageStandardEnum(String type, double ratio, String folderName) {
		this.type = type;
		this.ratio = ratio;
		this.folderName = folderName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
}
