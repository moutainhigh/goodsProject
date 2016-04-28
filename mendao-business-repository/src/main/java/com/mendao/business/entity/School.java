package com.mendao.business.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import com.mendao.framework.entity.FwAccount;

/**
 * 学校信息
 * @author libra
 *
 */
@Entity
@Table(name = "bu_school")
public class School  implements java.io.Serializable {

	private static final long serialVersionUID = -2297445333018450643L;
	
	@Id
	@Column(unique = true, nullable = false)
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToOne
	@JoinColumn(name = "id")
	private FwAccount fwAccount;
	/**
	 * 账户信息(机构收款账户)
	 * @return
	 */
	public FwAccount getFwAccount() {
		return fwAccount;
	}
	/**
	 * 账户信息(机构收款账户)
	 * @param fwAccount
	 */
	public void setFwAccount(FwAccount fwAccount) {
		this.fwAccount = fwAccount;
	}

	/**
	 * 学校名称
	 */
	@Column(length = 100, nullable = false, unique = true)
	private String schoolName;
	/**
	 * 学校名称
	 * @return
	 */
	public String getSchoolName() {
		return schoolName;
	}
	/**
	 * 学校名称
	 * @param schoolName
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
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

//	/**
//	 * 总校
//	 */
//	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
//	@JoinColumn(name = "parent_id")
//	private School parent;
//	/**
//	 * 总校
//	 * @return
//	 */
//	public School getParent() {
//		return parent;
//	}
//	/**
//	 * 总校
//	 * @param parent
//	 */
//	public void setParent(School parent) {
//		this.parent = parent;
//	}
	
//	/**
//	 * 排序顺序
//	 */
//	@Column(length = 50)
//	private String sortSeq;
//	/**
//	 * 排序顺序
//	 * @return
//	 */
//	public String getSortSeq() {
//		return sortSeq;
//	}
//	/**
//	 * 排序顺序
//	 * @param sortNo
//	 */
//	public void setSortSeq(String sortSeq) {
//		this.sortSeq = sortSeq;
//	}

	/**
	 * 学校类型
	 */
	@Column(nullable = false, length = 255)
	private String classification;
	/**
	 * 学校类型
	 * @return
	 */
	public String getClassification() {
		return classification;
	}
	/**
	 * 学校分类（Category的主键）
	 * @param category
	 */
	public void setClassification(String classification) {
		this.classification = classification;
	}

	
	/**
	 * 机构logo
	 */
	@Column(length = 1000)
	private String logoImage;
	
	public String getLogoImage() {
		return logoImage;
	}
	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	/**
	 * 学校联系电话
	 */
	@Column(length = 20)
	private String phone;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 学校授课模式
	 */
	@Column(length = 100)
	private String teachingMode;
	
	public String getTeachingMode() {
		return teachingMode;
	}
	public void setTeachingMode(String teachingMode) {
		this.teachingMode = teachingMode;
	}

	/**
	 * 机构简介
	 */
	@Type(type = "text")
	private String introduction;
	/**
	 * 机构简介
	 * @return
	 */
	public String getIntroduction() {
		return introduction;
	}
	/**
	 * 机构简介
	 * @param introduction
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	
	/**
	 * 招生对象
	 */
	@Column(length = 255)
	private String enrollObject;
	/**
	 * 招生对象
	 * @return
	 */
	public String getEnrollObject() {
		return enrollObject;
	}
	/**
	 * 招生对象
	 * @param enrollObject
	 */
	public void setEnrollObject(String enrollObject) {
		this.enrollObject = enrollObject;
	}
	/**
	 * 机构评价数目
	 */
	@Column(length = 11, nullable = false)
	@ColumnDefault(value = "0")
	private int commentNumber;

	public int getCommentNumber() {
		return commentNumber;
	}
	
	public void setCommentNumber(int commentNumber) {
		this.commentNumber = commentNumber;
	}

	@OneToMany(mappedBy = "school",fetch=FetchType.EAGER)
	private Set<SchoolSticker> schoolSticker;
	
	public Set<SchoolSticker> getSchoolSticker() {
		return schoolSticker;
	}
	public void setSchoolSticker(Set<SchoolSticker> schoolSticker) {
		this.schoolSticker = schoolSticker;
	}
	
	
}
