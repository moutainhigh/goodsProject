package com.mendao.framework.service;


import java.util.List;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.ShopUser;

public interface ShopUserService  {
	public ShopUser findByUuid(String uuid);
	PageEntity<ShopUser> findPage(PageEntity<ShopUser> pageBean);
	//用户注册
	public void register (ShopUser shopUser);
	//根据用户名查找用户
	public List<ShopUser> getUserByUserName(String userName);
	//根据电话查找
	public List<ShopUser> getUserByPhone(String phone);
}
