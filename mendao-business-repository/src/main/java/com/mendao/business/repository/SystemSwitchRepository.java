package com.mendao.business.repository;

import org.springframework.stereotype.Repository;

import com.mendao.business.entity.SystemSwitch;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("systemSwitchRepository")
public interface SystemSwitchRepository extends BaseRepository<SystemSwitch, Long>  {

	
}
