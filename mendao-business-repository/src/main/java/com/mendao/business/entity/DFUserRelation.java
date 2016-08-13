package com.mendao.business.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mendao.framework.entity.ShopUser;

@Entity
@Table(name = "t_user_relation")
public class DFUserRelation implements Serializable {

	private static final long serialVersionUID = 1326575234528101833L;

	@Id
	@GeneratedValue(generator = "identity")
	@GenericGenerator(name = "identity", strategy = "identity")
	@Column(unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", nullable = false)
	private ShopUser parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "child_id", nullable = false)
	private ShopUser child;

	private String desc;

	private int status;

	private Date createDate;

	private String ywDesc;

	private int type;

	/**
	 * 代理拥有的产品数
	 */
	@Column(nullable = true)
	private int allProductCount;

	/**
	 * 分销拥有的产品数
	 */
	@Column(nullable = true)
	private int hasProductCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ShopUser getParent() {
		return parent;
	}

	public void setParent(ShopUser parent) {
		this.parent = parent;
	}

	public ShopUser getChild() {
		return child;
	}

	public void setChild(ShopUser child) {
		this.child = child;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getAllProductCount() {
		return allProductCount;
	}

	public void setAllProductCount(int allProductCount) {
		this.allProductCount = allProductCount;
	}

	public int getHasProductCount() {
		return hasProductCount;
	}

	public void setHasProductCount(int hasProductCount) {
		this.hasProductCount = hasProductCount;
	}

	public String getYwDesc() {
		return ywDesc;
	}

	public void setYwDesc(String ywDesc) {
		this.ywDesc = ywDesc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
