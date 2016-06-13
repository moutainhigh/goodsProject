package com.mendao.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.SystemSwitch;
import com.mendao.business.repository.SystemSwitchRepository;
import com.mendao.business.service.SystemSwitchService;

@Service
public class SystemSwitchServiceImpl implements SystemSwitchService{

	@Autowired
	private SystemSwitchRepository systemSwitchRepository;
	
	@Override
	public List<SystemSwitch> getAll() {
		return (List<SystemSwitch>) systemSwitchRepository.findAll();
	}
	/**
	 * 保存付款图片修改
	 */
	@Override
	public SystemSwitch save(SystemSwitch payImage) {
		//先删除原有的   再保存
		systemSwitchRepository.deleteAll();
		return systemSwitchRepository.save(payImage);
	}

	
}
