package com.mendao.framework.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.RoleOperation;
import com.mendao.framework.repository.RoleOperationRepository;
import com.mendao.framework.service.RoleOperationService;

@Service
public class RoleOperationServiceImpl  implements RoleOperationService{

	@Autowired
	private RoleOperationRepository roleOperationRepository ;

	@Override
	public RoleOperation findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 分页查询
	 */
	@Override
	public PageEntity<RoleOperation> findPage(PageEntity<RoleOperation> pageBean) {
		return roleOperationRepository.findByPage(pageBean);
	}
	
	
}
