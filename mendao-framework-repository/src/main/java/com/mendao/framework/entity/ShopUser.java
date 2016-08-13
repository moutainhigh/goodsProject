package com.mendao.framework.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_shop_user")
public class ShopUser implements java.io.Serializable {

	private static final long serialVersionUID = 8054342788473662077L;
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

	@Column(length = 100, nullable = false)
	private String uuid;
	/**
	 * 用户名
	 */
	@Column(length = 50, nullable = false)
	private String userName;
	/**
	 * 用户角色UUID
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	
	/**
	 * 密码
	 */
	@Column(length = 100, nullable = false)
	private String password;
	/**
	 * 昵称
	 */
	@Column(length = 50, nullable = false)
	private String nickName;
	/**
	 * 邮箱
	 */
	@Column(length = 50, nullable = true)
	private String email;
	/**
	 * 电话
	 */
	@Column(length = 50, nullable = true)
	private String phone;

	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 到期时间
	 */
	private Date endDate;

	/**
	 * 用户状态 默认0（未激活） 1.正常 2 不可用 3 删除
	 */
	private int status;
	/**
	 * 用户备注
	 */
	private String remark;
	/**
	 * 用户好友数量限制
	 */
	private int friendNum;

	/**
	 * 用户头像
	 */
	private String avatar;
	/**
	 * 用户倒计时次数
	 */
	private int downCount;
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getFriendNum() {
		return friendNum;
	}

	public void setFriendNum(int friendNum) {
		this.friendNum = friendNum;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getDownCount() {
		return downCount;
	}

	public void setDownCount(int downCount) {
		this.downCount = downCount;
	}

}
