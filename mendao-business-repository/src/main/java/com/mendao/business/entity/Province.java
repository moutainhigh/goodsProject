package com.mendao.business.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mendao.common.util.StringUtil;


/**
 * 省份表
 * 
 */
@Entity
@Table(name="bu_province")
public class Province implements java.io.Serializable {
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
	 * 省份名称
	 */	
	@Column(length=20)
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length = 50)
	private String sortSeq;
	public String getSortSeq() {
		return sortSeq;
	}
	public void setSortSeq(String sortSeq) {
		this.sortSeq = sortSeq;
	}
	
	
	@Transient
	private List<City> citys;
	public List<City> getCitys() {
		return citys;
	}
	public void setCitys(List<City> citys) {
		this.citys = citys;
	}
}