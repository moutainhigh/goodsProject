package com.mendao.framework.enums;

import java.util.List;

import com.mendao.framework.entity.Menu;
import com.mendao.framework.entity.ShopUser;

public class UserUtil implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4206740159968638034L;

	private ShopUser shopUser;

	private Long id;

	public ShopUser getShopUser() {
		return shopUser;
	}

	public void setShopUser(ShopUser shopUser) {
		this.shopUser = shopUser;
		this.id = shopUser.getId();
		this.roleId = shopUser.getRole().getId();
		this.userName = shopUser.getUserName();
		this.email = shopUser.getEmail();
		this.phone = shopUser.getPhone();
	}

	public Long getId() {
		return null == id ? 0L : id;
	}

	public void setId(Long id) {
		this.id = shopUser.getId();
	}

	private String userName;

	private String email;

	private String phone;

	private Long roleId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = shopUser.getUserName();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = shopUser.getEmail();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = shopUser.getPhone();
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = shopUser.getRole().getId();
	}

	// 用户可操作的菜单列表
	private List<Menu> menuList;

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
