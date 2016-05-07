package com.mendao.framework.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.common.util.StringUtil;
import com.mendao.exception.BusinessCheckException;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.Menu;
import com.mendao.framework.entity.Role;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.entity.UserRelation;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.repository.MenuRepository;
import com.mendao.framework.repository.ShopUserRepository;
import com.mendao.framework.repository.UserRelationRepository;
import com.mendao.framework.service.RoleService;
import com.mendao.framework.service.ShopUserService;
import com.mendao.util.EncryptService;
import com.mendao.util.RegexUtil;

@Service
public class ShopUserServiceImpl implements ShopUserService {

	@Autowired
	EncryptService encryptService;

	@Autowired
	private ShopUserRepository shopUserRepository;

	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	UserRelationRepository userRelationRepository;
	 
	@Autowired
	RoleService roleService;
	
	

	@Override
	public ShopUser findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 分页查询
	 */
	@Override
	public PageEntity<ShopUser> findPage(PageEntity<ShopUser> pageBean) {
		return shopUserRepository.findByPage(pageBean);
	}

	/**
	 * 用户注册
	 */
	@Override
	public ShopUser register(ShopUser shopUser,String uuid) {
		shopUser.setPassword(encryptService.encrypt(shopUser.getPassword()));
		shopUser.setCreateDate(new Date());
		shopUser.setEndDate(getDateAfter(new Date(),30));
		shopUser.setStatus(1);
		shopUser.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
		shopUserRepository.save(shopUser);
		//创建推荐用户关系
		if(uuid != null && !uuid.equals("")){
			createUserRelation(shopUser,uuid);
		}
		
		return shopUser;
	}

	/*
	 * 根据用户username获得用户
	 */
	@Override
	public List<ShopUser> getUserByUserName(String userName) {
		return shopUserRepository.getUserByUserName(userName);
	}

	/*
	 * 根据用户phone获得用户
	 */
	@Override
	public List<ShopUser> getUserByPhone(String phone) {
		return shopUserRepository.getUserByPhone(phone);
	}

	@Override
	public UserUtil login(String userName, String password) {
		password = encryptService.encrypt(password);
		ShopUser shopUser = shopUserRepository.login(userName, password);
		UserUtil userUtil = new UserUtil();
		if (shopUser != null) {
			userUtil.setShopUser(shopUser);
			if (shopUser.getEndDate().getTime() < new Date().getTime()) {
				userUtil.setMessage("用户名或者密码错误");
				return userUtil;
			}
			// 设置用户权限菜单
			initUserPermission(userUtil);
		} else {
			userUtil.setMessage("用户名或者密码错误");
		}
		return userUtil;
	}

	/**
	 * 设置用户可操作的菜单
	 * 
	 * @param userUtil
	 */
	private void initUserPermission(UserUtil userUtil) {
		String hql = "select t from Menu t where t.id in (select s.menu.id from RoleOperation s where s.role.id = "
				+ userUtil.getRoleId() + " ) order by t.sort asc ";
		List<Menu> menuList = menuRepository.findListByHql(hql);
		userUtil.setMenuList(menuList);
	}
	/**
	 * 获取当前时间的几天后的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
	/**
	 * 为注册用户获取增加用户关系
	 * @param id
	 * @param uuid
	 */
	private void createUserRelation(ShopUser user, String uuid) {
		ShopUser parentUser = shopUserRepository.getUserByUuid(uuid);
		if(parentUser != null){
			UserRelation userRelation = new UserRelation();
			userRelation.setParent(parentUser);
			userRelation.setCurrentUser(user);
			userRelation.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
			userRelationRepository.save(userRelation);
			//增加层级推荐关系
			UserRelation urnew = new UserRelation();
			UserRelation ur = userRelationRepository.getUserRelationByCurrentUserId(parentUser.getId());
			for(int i=0;i<4;i++){
				if(ur != null){
					urnew.setParent(ur.getParent());
					urnew.setCurrentUser(user);
					urnew.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
					userRelationRepository.save(urnew);
					ur = userRelationRepository.getUserRelationByCurrentUserId(ur.getParent().getId());
				}else{
					break;
				}
			}
		}
	}
	/**
	 * 分页查找
	 */
	@Override
	public PageEntity<ShopUser> getPage(PageEntity<ShopUser> pageEntity) {
		return shopUserRepository.findByPage(pageEntity);
	}

}
