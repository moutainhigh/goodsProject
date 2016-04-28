package com.mendao.business.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mendao.framework.entity.FwAccount;

@Entity
@Table(name = "bu_user")
public class UserInfo implements java.io.Serializable {

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
	
	@OneToOne()
	@JoinColumn(name = "id", nullable = false)
	private FwAccount fwAccount;
	/**
	 * 用户账号
	 * @return
	 */
	public FwAccount getFwAccount() {
		return fwAccount;
	}
	/**
	 * 用户账号
	 * @param fwAccount
	 */
	public void setFwAccount(FwAccount fwAccount) {
		this.fwAccount = fwAccount;
	}

	/**
	 * 用户昵称
	 */
	@Column(length = 100)
	private String nickName;
	/**
	 * 用户昵称
	 * @return
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * 用户昵称
	 * @param userName
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	/**
	 * 年龄
	 */
	private int age = 0;
	/**
	 * 年龄
	 * @return
	 */
	public int getAge() {
		return age;
	}
	/**
	 * 年龄
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * 用户地域（省市区）
	 */
	@Column(length = 255)
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
	
	private int gender;
	/**
	 * 用户性别。0:男士；1:女士
	 * @return
	 */
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}

	/**
	 * 用户头像地址
	 */
	@Column(length = 200)
	private String icon;
	/**
	 * 用户头像地址
	 * @return
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * 用户头像地址
	 * @param icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	/**
	 * 个人简介
	 */
	@Column(length = 255)
	private String summary;
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	/**
	 * 用户绑定的手机号
	 */
	@Column(length = 20)
	private String mobile;
	/**
	 * 用户绑定的手机号
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 用户绑定的手机号
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * 用户绑定的邮箱地址
	 */
	@Column(length = 50)
	public String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 学龄阶段、年级等。
	 * JSON格式
	 */
	private String section;
	/**
	 * 学龄阶段、年级等。
	 * JSON格式
	 * @return
	 */
	public String getSection() {
		return section;
	}
	/**
	 * 学龄阶段、年级等。
	 * JSON格式
	 * @param section
	 */
	public void setSection(String section) {
		this.section = section;
	}
	public UserInfo(){
		this.fwAccount = new FwAccount();
		this.gender = 0;
	}
	/**
	 * 关注的标签
	 */
	private String followSticker;
	
	public String getFollowSticker() {
		return followSticker;
	}
	public void setFollowSticker(String followSticker) {
		this.followSticker = followSticker;
	}
	
	
}
