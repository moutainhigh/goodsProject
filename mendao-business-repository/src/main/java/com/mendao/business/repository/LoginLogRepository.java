package com.mendao.business.repository;

import org.springframework.stereotype.Repository;

import com.mendao.business.entity.LoginLog;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("loginLogRepository")
public interface LoginLogRepository extends BaseRepository<LoginLog, Long>  {


	
}
