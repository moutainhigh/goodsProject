package com.mendao.framework.service.impl;



import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.common.util.StringUtil;
import com.mendao.exception.BusinessCheckException;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.Role;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.repository.ShopUserRepository;
import com.mendao.framework.service.RoleService;
import com.mendao.framework.service.ShopUserService;
import com.mendao.util.EncryptService;
import com.mendao.util.RegexUtil;

@Service
public class ShopUserServiceImpl  implements ShopUserService{

	@Autowired
	EncryptService encryptService;
	
	@Autowired
	private ShopUserRepository shopUserRepository;
	
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
	public void register(ShopUser shopUser){
		
		shopUser.setPassword(encryptService.encrypt(shopUser.getPassword()));
		shopUser.setCreateDate(new Date());
		shopUser.setEndDate(new Date());
		shopUser.setStatus(1);
		shopUser.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
		
		shopUserRepository.save(shopUser);
	}

	@Override
	public List<ShopUser> getUserByUserName(String userName) {
		return shopUserRepository.getUserByUserName(userName);
	}

	@Override
	public List<ShopUser> getUserByPhone(String phone) {
		return shopUserRepository.getUserByPhone(phone);
	}
	
	
}
