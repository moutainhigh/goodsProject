package com.mendao.framework.service;


import java.util.List;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.Role;

public interface RoleService  {
	public Role findById(Long id);
	PageEntity<Role> findPage(PageEntity<Role> pageBean);
	public List<Role> getAllRole();
	public Role add(Role role);
	public void deleteById(Long id);
}
