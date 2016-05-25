package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.FProduct;
import com.mendao.business.entity.PKind;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.ShopUser;

/**
 * @ClassName: ProductService 
 * @Description: TODO 
 * @author TianMeifeng
 * @date 2016年5月8日 上午1:26:03 
 *
 */
public interface ProductService  {
	
	/**
	 * 分页查找 代理
	 * @param pageEntity
	 * @return
	 */
	public PageEntity<DProduct> getDProductPage(PageEntity<DProduct> pageEntity);
	
	/**
	 * 分页查找 分销
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
	 * 修改分销产品
	 * @Title: updateFProduct 
	 * @Description: TODO
	 * @param @param fProduct    
	 * @return void  
	 * @throws
	 */
	public void updateFProduct(FProduct fProduct);
	
	/**
	 * 批量修改产品状态
	 * @Title: updateProductStatus 
	 * @Description: TODO
	 * @param @param ids    
	 * @return void  
	 * @throws
	 */
	public void updateProductStatus(Integer status, String ids);
	
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

	/**
	 * 通过id删除产品 代理产品
	 * @Title: deleteDProductById 
	 * @Description: TODO
	 * @param @param id    
	 * @return void  
	 * @throws
	 */
	public void deleteDProductById(Long id);
	
	/**
	 * 通过id删除分销产品
	 * @Title: deleteFProductById 
	 * @Description: TODO
	 * @param @param id    
	 * @return void  
	 * @throws
	 */
	public void deleteFProductById(Long id);
	
	/**
	 * 通过当前分销的ID 获得他的所有代理
	 * @Title: getAllDaiLiByCurrentUserId 
	 * @Description: TODO
	 * @param @param id
	 * @param @return    
	 * @return List<ShopUser>  
	 * @throws
	 */
	public List<ShopUser> getAllDaiLiByCurrentUserId(Long id);
	
	/**
	 * 批量修改分销产品上下架
	 * @Title: updateFProductOnSale 
	 * @Description: TODO
	 * @param @param onSale
	 * @param @param ids    
	 * @return boolean  
	 * @throws
	 */
	public boolean updateFProductOnSale(Integer onSale, String ids);
	/**
	 * 根据tDProduct的ID查找tDProduct
	 * @param id
	 * @return
	 */
	public FProduct getDProductById(Long id);
	/**
	 * 根据modifyUserId查找该业务其他在售产品
	 * @param modifyUserId
	 * @param id
	 * @param limit
	 * @return
	 */
	public List<FProduct> getByModifyUserId(Long modifyUserId, Long id,int limit);
	/**
	 * 根据业务id查找kind
	 * @param id
	 * @return
	 */
	public List<PKind> queryAllByYewuId(Long id);
}
