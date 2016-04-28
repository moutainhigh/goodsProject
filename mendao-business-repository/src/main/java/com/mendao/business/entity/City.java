package com.mendao.business.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 城市表
 * 
 */
@Entity
@Table(name="bu_city")
public class City implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -2297445333018450643L;
	@Id
	@Column(unique = true, nullable = false)
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 城市名称
	 */
	@Column(length=20)
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private int provinceId;
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	
	/**
	 * 排序
	 */
	@Column(length = 50)
	private String sortSeq;
	public String getSortSeq() {
		return sortSeq;
	}
	public void setSortSeq(String sortSeq) {
		this.sortSeq = sortSeq;
	}
	
	@Transient
	private List<District> districts;
	public List<District> getDistricts() {
		return districts;
	}
	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}
	
}