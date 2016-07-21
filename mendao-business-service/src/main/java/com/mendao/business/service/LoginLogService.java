package com.mendao.business.service;

import com.mendao.business.entity.LoginLog;
import com.mendao.framework.base.jpa.PageEntity;



public interface LoginLogService {

	/**
	 * 分页查找
	 * @param pageEntity
	 * @return
	 */
	public PageEntity<LoginLog> getPage(PageEntity<LoginLog> pageEntity);
	
	void save(LoginLog loginLog);
}
