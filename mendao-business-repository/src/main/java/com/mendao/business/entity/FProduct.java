package com.mendao.business.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mendao.framework.entity.ShopUser;

/**
 * 
 * @ClassName: DProduct 
 * @Description: 分销产品类
 * @author TianMeifeng
 * @date 2016年5月6日 下午12:07:05 
 *
 */
@Entity
@Table(name = "t_f_product")
public class FProduct implements Serializable{
	
	/** 
	 * @Fields serialVersionUID : 
	 */ 
	private static final long serialVersionUID = 4361003568983071233L;

	/**
	 * 产品id
	 */
	@Id
	@GeneratedValue(generator = "identity")
	@GenericGenerator(name = "identity", strategy = "identity")
	@Column(unique = true, nullable = false)
	private Long id;
	
	/**
	 * 产品名称
	 */
	@Column(length = 100, nullable = false)
	private String pName;
	
	/**
	 * 产品描述
	 */
	
	@Column(length = 500, nullable = true)
	private String desc;
	
	/**
	 * 产品价格
	 */
	@Column(length = 11, nullable = true)
	private Float price;
	
	/**
	 * 产品状态 0在售 1补货2售空
	 */
	@Column(length = 11, nullable = true)
	private Integer status;
	
	/**
	 * 类目 id
	 */
	@Column(length = 200, nullable = true)
	private String kindId;
	
	/**
	 * 创建人id
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = false)
	private ShopUser createUserId;
	
	/**
	 * 创建时间
	 */
	@Column(nullable = false)
	private Date createTime;
	
	/**
	 * 修改人id
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modify_user_id", nullable = true)
	private ShopUser modifyUserId;
	
	/**
	 * 前台显示类目
	 */
	@Column(length = 11, nullable = true)
	private String showKind;
	
	/**
	 * 备用字段
	 */
	@Column(length = 200, nullable = false)
	private String comment;
	
	/**
	 * 删除标记
	 */
	@Column(length = 10, nullable = false)
	private Integer deleteFlag;
	
	/**
	 * 对应的代理产品
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "d_product", nullable = true)
	private DProduct dProduct;
	
	/**
	 * 是否上架 1 上架 2 下架
	 */
	@Column(length = 10, nullable = false)
	private Integer onSale;
	
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

	public ShopUser getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(ShopUser createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public ShopUser getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(ShopUser modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public String getShowKind() {
		return showKind;
	}

	public void setShowKind(String showKind) {
		this.showKind = showKind;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public Integer getOnSale() {
		return onSale;
	}

	public void setOnSale(Integer onSale) {
		this.onSale = onSale;
	}
	
}
