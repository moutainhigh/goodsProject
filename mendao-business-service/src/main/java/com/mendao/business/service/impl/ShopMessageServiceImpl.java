package com.mendao.business.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.ShopMessage;
import com.mendao.business.repository.ShopMessageRepository;
import com.mendao.business.service.ShopMessageService;

@Service
public class ShopMessageServiceImpl implements ShopMessageService{

	@Autowired
	private ShopMessageRepository shopMessageRepository;
	
	/**
	 * 根据user_id查找
	 */
	@Override
	public ShopMessage findByUserId(Long userId) {
		return shopMessageRepository.findByUserId(userId);
	}

	@Override
	public ShopMessage save(ShopMessage shopMessage) {
		return shopMessageRepository.save(shopMessage);
	}

	@Override
	public void update(ShopMessage shopMessage) {
		shopMessageRepository.merge(shopMessage);
	}
	
	
}
