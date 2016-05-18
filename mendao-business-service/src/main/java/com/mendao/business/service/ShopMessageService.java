package com.mendao.business.service;

import com.mendao.business.entity.ShopMessage;


public interface ShopMessageService {

	/**
	 * 根据user_id查找
	 * @param id
	 * @return
	 */
	ShopMessage findByUserId(Long userId);

	ShopMessage save(ShopMessage shopMessage);

	void update(ShopMessage shopMessage);

	
}
