package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.FShowProduct;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.ShopUser;


public interface FShowProductService {

	PageEntity<FShowProduct> getProductPage(PageEntity<FShowProduct> pageEntity);

	void deleteById(Long userId, Long id);

	void addProductToProxy(ShopUser dUser, ShopUser proxyUser, String ids);

	List<Long> getDProductByUserId(Long proxyId);

	
}
