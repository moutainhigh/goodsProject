package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.FShowProduct;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.ShopUser;


public interface FShowProductService {

	PageEntity<FShowProduct> getProductPage(PageEntity<FShowProduct> pageEntity);

	void deleteById(Long userId, Long id);

	void addProductToProxy(ShopUser dUser, ShopUser proxyUser, String ids);

	List<Long> getDProductByUserId(Long proxyId);
	/**
	 * 将代理的所有产品添加到业务
	 * @param findById
	 * @param shopUser
	 */
	void addAllProductToProxy(ShopUser findById, ShopUser shopUser);
	/**
	 * 代理添加产品时将产品添加到业务
	 * @param child
	 * @param dProduct
	 */
	void addProductToAllProxy(ShopUser child, DProduct dProduct);

	
}
