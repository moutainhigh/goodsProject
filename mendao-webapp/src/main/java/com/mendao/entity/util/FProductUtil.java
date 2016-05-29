package com.mendao.entity.util;

import java.util.List;

import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.ProductPic;

public class FProductUtil {

	private Long id;

	private String pName;

	private String desc;

	private Float price;

	private String videoUrl;

	private Integer status;

	private String kindId;

	private String kindString;

	private String showKind;

	private int changeFlag;

	private DProduct dProduct;

	private String firstImage;

	private List<ProductPic> imageList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getKindId() {
		return kindId;
	}

	public void setKindId(String kindId) {
		this.kindId = kindId;
	}

	public String getKindString() {
		return kindString;
	}

	public void setKindString(String kindString) {
		this.kindString = kindString;
	}

	public String getShowKind() {
		return showKind;
	}

	public void setShowKind(String showKind) {
		this.showKind = showKind;
	}

	public int getChangeFlag() {
		return changeFlag;
	}

	public void setChangeFlag(int changeFlag) {
		this.changeFlag = changeFlag;
	}

	public DProduct getdProduct() {
		return dProduct;
	}

	public void setdProduct(DProduct dProduct) {
		this.dProduct = dProduct;
	}

	public String getFirstImage() {
		return firstImage;
	}

	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}

	public List<ProductPic> getImageList() {
		return imageList;
	}

	public void setImageList(List<ProductPic> imageList) {
		this.imageList = imageList;
	}

}
