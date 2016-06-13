package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.SystemSwitch;

public interface SystemSwitchService {

	List<SystemSwitch> getAll();

	SystemSwitch save(SystemSwitch systemSwitch);

	
}
