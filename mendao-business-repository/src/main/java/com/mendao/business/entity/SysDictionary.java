package com.mendao.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="sys_dictionary")
public class SysDictionary implements java.io.Serializable {
	private static final long serialVersionUID = -2297445333018450643L;
	@Id
	@GeneratedValue(generator = "identity")
	@GenericGenerator(name = "identity", strategy = "identity")
	@Column(unique = true, nullable = false)
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 字典code
	 */
	@Column(length = 20, nullable = false)
	private String dicCode;
	/**
	 * 字典key
	 */
	@Column(length = 50, nullable = false)
	private String dicKey;
	/**
	 * 字典value
	 */
	@Column(length = 50, nullable = false)
	private String dicValue;
	/**
	 * 字典描述
	 */
	@Column(length = 255)
	private String remark;
	/**
	 * 字典排序
	 */
	@Column(length = 11 ,nullable = false)
	@ColumnDefault(value = "0")
	private int sort;
	
	public String getDicKey() {
		return dicKey;
	}
	public String getDicCode() {
		return dicCode;
	}
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}
	public void setDicKey(String dicKey) {
		this.dicKey = dicKey;
	}
	public String getDicValue() {
		return dicValue;
	}
	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
}
