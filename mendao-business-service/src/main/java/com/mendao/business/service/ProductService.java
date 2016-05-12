package com.mendao.business.service;

import java.util.List;

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
	 * 添加代理产品
	 * @Title: addDProduct 
	 * @Description: TODO
	 * @param @param dProduct
	 * @param @return    
	 * @return DProduct  
	 * @throws
	 */
	public DProduct addDProduct(DProduct dProduct);
	
	/**
	 * 通过id查找代理产品
	 * @Title: findDProductById 
	 * @Description: TODO
	 * @param @param id
	 * @param @return    
	 * @return DProduct  
	 * @throws
	 */
	public DProduct findDProductById(Long id);
	
	/**
	 * 修改代理产品
	 * @Title: updateDProduct 
	 * @Description: TODO
	 * @param @param dProduct    
	 * @return void  
	 * @throws
	 */
	public void updateDProduct(DProduct dProduct);
	
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
	
	/**
	 * 查询所有类目 通过创建人的id
	 */
	public List<PKind> queryAllPropertiesByCreateId(Long id);
}
