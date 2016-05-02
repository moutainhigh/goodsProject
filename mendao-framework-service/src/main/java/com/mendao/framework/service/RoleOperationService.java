package com.mendao.framework.service;


import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.RoleOperation;

public interface RoleOperationService  {
	
	public RoleOperation findByUuid(String uuid);
	
	PageEntity<RoleOperation> findPage(PageEntity<RoleOperation> pageBean);
}
