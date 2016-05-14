package com.mendao.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.PayMessage;
import com.mendao.business.repository.PayMessageRepository;
import com.mendao.business.service.PayMessageService;
import com.mendao.framework.base.jpa.PageEntity;

@Service
public class PayMessageServiceImpl implements PayMessageService{

	@Autowired
	private PayMessageRepository payMessageRepository;
	/**
	 * 分页查找
	 */
	@Override
	public PageEntity<PayMessage> getPage(PageEntity<PayMessage> pageEntity) {
		return payMessageRepository.findByPage(pageEntity);
	}
	/**
	 * 根据ID删除
	 */
	@Override
	public void deleteById(Long id) {
		PayMessage payMessage = payMessageRepository.findOne(id);
		payMessage.setStatus(0);
		payMessageRepository.merge(payMessage);
	}
	/**
	 * 保存付款信息
	 */
	@Override
	public void saveMessage(PayMessage payMessage) {
		payMessageRepository.save(payMessage);
	}

	
}
