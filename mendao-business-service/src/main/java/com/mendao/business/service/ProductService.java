package com.mendao.business.service;

import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.FProduct;
import com.mendao.framework.base.jpa.PageEntity;

/**
 * @ClassName: ProductService 
 * @Description: TODO 
 * @author TianMeifeng
 * @date 2016年5月8日 上午1:26:03 
 *
 */
public interface ProductService  {
	
	/**
	 * 分页查找 分销
	 * @param pageEntity
	 * @return
	 */
	public PageEntity<DProduct> getDProductPage(PageEntity<DProduct> pageEntity);
	
	/**
	 * 分页查找 代理
	 * @param pageEntity
	 * @return
	 */
	public PageEntity<FProduct> getFProductPage(PageEntity<FProduct> pageEntity);
}
