package com.mendao.business.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.LoginLog;
import com.mendao.business.repository.LoginLogRepository;
import com.mendao.business.service.LoginLogService;
import com.mendao.framework.base.jpa.PageEntity;

@Service
public class LoginLogServiceImpl implements LoginLogService{

	@Autowired
	private LoginLogRepository loginLogRepository;

	@Override
	public PageEntity<LoginLog> getPage(PageEntity<LoginLog> pageEntity) {
		return loginLogRepository.findByPage(pageEntity);
	}

	@Override
	public void save(LoginLog loginLog) {
		loginLogRepository.save(loginLog);
	}
	

	
}
