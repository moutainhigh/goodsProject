package com.mendao.entity.util;

import java.util.Date;
import java.util.List;

import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.ProductPic;
import com.mendao.framework.entity.ShopUser;

public class FProductUtil {

	private Long id;

	private String pName;

	private String desc;

	private String price;

	private String videoUrl;

	private Integer status;

	private String kindId;

	private String kindString;

	private String showKind;

	private Integer onSale;

	private int changeFlag;

	private Integer deleteFlag;

	private ShopUser createUserId;

	private ShopUser modifyUserId;

	private DProduct dProduct;

	private String firstImage;

	private List<ProductPic> imageList;

	private Date createTime;

	private String parentDesc;

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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
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

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	public ShopUser getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(ShopUser createUserId) {
		this.createUserId = createUserId;
	}

	public ShopUser getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(ShopUser modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public String getParentDesc() {
		return parentDesc;
	}

	public void setParentDesc(String parentDesc) {
		this.parentDesc = parentDesc;
	}

	public Integer getOnSale() {
		return onSale;
	}

	public void setOnSale(Integer onSale) {
		this.onSale = onSale;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
