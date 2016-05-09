package com.mendao.business.service;

import com.mendao.business.entity.PayMessage;
import com.mendao.framework.base.jpa.PageEntity;

public interface PayMessageService {

	PageEntity<PayMessage> getPage(PageEntity<PayMessage> pageEntity);

	void deleteById(Long id);

	
}
