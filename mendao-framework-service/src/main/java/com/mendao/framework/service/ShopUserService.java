package com.mendao.framework.service;


import java.util.List;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.enums.UserUtil;

public interface ShopUserService  {
	public ShopUser findByUuid(String uuid);
	PageEntity<ShopUser> findPage(PageEntity<ShopUser> pageBean);
	//用户注册
	public ShopUser register (ShopUser shopUser,String uuid);
	//根据用户名查找用户
	public List<ShopUser> getUserByUserName(String userName);
	//根据电话查找
	public List<ShopUser> getUserByPhone(String phone);
	//用户登录
	public UserUtil login(String userName, String password);
	/**
	 * 分页查找
	 * @param pageEntity
	 * @return
	 */
	public PageEntity<ShopUser> getPage(PageEntity<ShopUser> pageEntity);
	/**
	 * 后台管理员添加用户
	 * @param shopUser
	 */
	public ShopUser addUser(ShopUser shopUser);
	/**
	 * 根据ID查找用户
	 * @param id
	 * @return
	 */
	public ShopUser findById(Long id);
	/**
	 * 用户信息更新
	 * @param shopUser
	 */
	public void updateUser(ShopUser shopUser);
	/**
	 * 删除用户
	 * @param id
	 */
	public void deleteById(Long id);
	/**
	 * 将用户的密码重置为111111
	 * @param id
	 */
	public void resetPasswordById(Long id);
}
