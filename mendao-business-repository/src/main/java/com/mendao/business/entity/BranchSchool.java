package com.mendao.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "bu_branch_school")
public class BranchSchool implements java.io.Serializable {

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
	 * 分校名称
	 */
	private String schoolName;
	/**
	 * 分校名称
	 * @return
	 */
	public String getSchoolName() {
		return schoolName;
	}
	/**
	 * 分校名称
	 * @param schoolName
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
 	/**
 	 * 总校
 	 */
 	@ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name = "parent_id")
 	private School parent;
 	/**
 	 * 总校
 	 * @return
 	 */
 	public School getParent() {
 		return parent;
 	}
 	/**
 	 * 总校
 	 * @param parent
 	 */
 	public void setParent(School parent) {
 		this.parent = parent;
 	}
 	
 	/**
	 * 用户地域（省市区）
	 */
	@Column(length = 50)
	private String district;
	/**
	 * 用户地域（省市区）
	 * @return
	 */
	public String getDistrict() {
		return district;
	}
	/**
	 * 用户地域（省市区）
	 * @param address
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * 学校地址
	 */
	@Column(length = 200)
	private String address;
	/**
	 * 学校地址
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 学校地址
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
 	
	
 	/**
 	 * 排序顺序
 	 */
	@Column(length = 11 ,nullable = false)
	@ColumnDefault(value = "0")
 	private int sortSeq;
 	/**
 	 * 排序顺序
 	 * @return
 	 */
 	public int getSortSeq() {
 		return sortSeq;
 	}
 	/**
 	 * 排序顺序
 	 * @param sortNo
 	 */
 	public void setSortSeq(int sortSeq) {
 		this.sortSeq = sortSeq;
 	}
}
