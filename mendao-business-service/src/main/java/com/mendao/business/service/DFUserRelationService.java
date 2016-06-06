package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.DFUserRelation;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.ShopUser;

public interface DFUserRelationService {

	PageEntity<DFUserRelation> getPage(PageEntity<DFUserRelation> pageEntity);

	List<Long> getChildId(Long id);
	
	void addUserToProxy(Long parendId, ShopUser shopUser);

	void deleteById(Long id);

	List<DFUserRelation> getListByProperty(Long parentId, Long childId);

	/**
	 * 通过id 查询到每个代理下的产品数的集合
	 * @Title: queryAllDProductByIds 
	 * @Description: TODO
	 * @param @param ids
	 * @param @return    
	 * @return List<Object>  
	 * @throws
	 */
	List<Object> queryAllDProductByIds(List<Long> ids);
	
	/**
	 * 通过id查询到代理下可见的产品输的集合
	 * @Title: queryHasFProductByIds 
	 * @Description: TODO
	 * @param @param ids
	 * @param @return    
	 * @return List<Object>  
	 * @throws
	 */
	List<Object> queryHasFProductByIds(List<Long> ids);
	
	/**
	 * 修改分对代理设置的标签
	 */
	boolean updateDesc(String message, Long id);

	List<DFUserRelation> getByProperty(Long id, Long id2);
	/**
	 * 根据parentID获取list
	 * @param id
	 * @return
	 */
	List<DFUserRelation> getByParentId(Long id);
	/**
	 * 根据modifyUserId和createUserId获取产品个数
	 * @param modifyUserId
	 * @param createUserId
	 * @return
	 */
	int queryHasFProductById(Long modifyUserId, Long createUserId);
	
}
