package com.mendao.framework.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.Role;
import com.mendao.framework.repository.RoleRepository;
import com.mendao.framework.service.RoleService;

@Service
public class RoleServiceImpl  implements RoleService{

	@Autowired
	private RoleRepository roleRepository ;

	@Override
	public Role findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return roleRepository.findByUuid(uuid);
	}
	
	/**
	 * 分页查询
	 */
	@Override
	public PageEntity<Role> findPage(PageEntity<Role> pageBean) {
		return roleRepository.findByPage(pageBean);
	}

	@Override
	public Role findById(Long id) {
		return roleRepository.findOne(id);
	}
	/**
	 * 获取role列表
	 */
	@Override
	public List<Role> getAllRole() {
		return roleRepository.getAllRole();
	}
	
	
}
