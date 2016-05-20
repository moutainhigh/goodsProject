package com.mendao.business.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.DFUserRelation;
import com.mendao.business.repository.DFUserRelationRepository;
import com.mendao.business.service.DFUserRelationService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.repository.ShopUserRepository;

@Service
public class DFUserRelationServiceImpl implements DFUserRelationService{

	@Autowired
	private DFUserRelationRepository dFUserRelationRepository; 
	
	@Autowired
	private ShopUserRepository shopUserRepository;
	
	/**
	 * 获取列表   分页
	 */
	@Override
	public PageEntity<DFUserRelation> getPage(PageEntity<DFUserRelation> pageEntity) {
		return dFUserRelationRepository.findByPage(pageEntity);
	}
	/**
	 * 
	 */
	@Override
	public List<Long> getChildId(Long id) {
		return dFUserRelationRepository.getChildId(id);
	}
	/**
	 * 代理批量添加分销商
	 */
	@Override
	public void addUserToProxy(Long parendId, ShopUser user) {
		DFUserRelation df = new DFUserRelation();
		df.setChild(user);
		df.setParent(shopUserRepository.findOne(parendId));
		df.setCreateDate(new Date());
		df.setStatus(2);
		dFUserRelationRepository.save(df);
	}
	/**
	 * 代理删除分销商
	 */
	@Override
	public void deleteById(Long id) {
		dFUserRelationRepository.delete(id);
	}
	/**
	 * 根据parentId和childId获取记录
	 */
	@Override
	public List<DFUserRelation> getListByProperty(Long parentId, Long childId) {
		return dFUserRelationRepository.getListByProperty(parentId, childId);
	}
	@Override
	public List<Object> queryAllDProductByIds(List<Long> ids) {
		return dFUserRelationRepository.getAllDProductByIds(ids);
	}
	@Override
	public List<Object> queryHasFProductByIds(List<Long> ids) {
		return dFUserRelationRepository.getHasFProductByIds(ids);
	}

	public boolean updateDesc(String message, Long id){
		try{
			dFUserRelationRepository.updateDFUserRelationDesc(message, id);
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
