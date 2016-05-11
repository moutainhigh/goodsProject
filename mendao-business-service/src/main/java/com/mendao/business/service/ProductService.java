package com.mendao.business.service;

import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.FProduct;
import com.mendao.business.entity.PKind;
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
	
	/**
	 * 查询产品类目
	 * @Title: getPKindPage 
	 * @Description: TODO
	 * @param @param pageEntity
	 * @param @return    
	 * @return PageEntity<PKind>  
	 * @throws
	 */
	public PageEntity<PKind> getPKindPage(PageEntity<PKind> pageEntity);
	
	/**
	 * 添加类目
	 */
	public PKind addPKind(PKind pKind);
	
	/**
	 * 通过id 查找类目
	 */
	public PKind findById(Long id);
	
	/**
	 * 修改类目
	 */
	public void update(PKind pKind);
	
	/**
	 * 删除类目
	 */
	public void deletePKindById(Long id);
}
