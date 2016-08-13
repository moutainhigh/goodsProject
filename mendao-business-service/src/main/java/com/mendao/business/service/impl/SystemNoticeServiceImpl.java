package com.mendao.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.SystemNotice;
import com.mendao.business.repository.SystemNoticeRepository;
import com.mendao.business.service.SystemNoticeService;

@Service
public class SystemNoticeServiceImpl implements SystemNoticeService{

	@Autowired
	private SystemNoticeRepository systemNoticeRepository;
	
	@Override
	public List<SystemNotice> getAll() {
		return (List<SystemNotice>) systemNoticeRepository.findAll();
	}
	/**
	 * 保存公告修改
	 */
	@Override
	public SystemNotice save(SystemNotice systemNotice) {
		//先删除原有的   再保存
		systemNoticeRepository.deleteAll();
		return systemNoticeRepository.save(systemNotice);
	}

	
}
