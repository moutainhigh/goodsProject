package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.SystemNotice;

public interface SystemNoticeService {

	List<SystemNotice> getAll();

	SystemNotice save(SystemNotice systemNotice);

	
}
