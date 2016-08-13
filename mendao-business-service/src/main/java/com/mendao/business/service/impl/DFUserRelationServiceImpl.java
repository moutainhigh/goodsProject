package com.mendao.business.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.DFUserRelation;
import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.FProduct;
import com.mendao.business.entity.FShowProduct;
import com.mendao.business.entity.ProductPic;
import com.mendao.business.repository.DFUserRelationRepository;
import com.mendao.business.repository.DProductRepository;
import com.mendao.business.repository.FProductRepository;
import com.mendao.business.repository.FShowProductRepository;
import com.mendao.business.repository.ProductPicRepository;
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
	
	@Autowired
	private FProductRepository fProductRepository;
	
	@Autowired
	private FShowProductRepository fShowProductRepository;
	
	@Autowired
	private DProductRepository dProductRepository;
	
	
	@Autowired
	private ProductPicRepository productPicRepository;
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
	 * 好友添加
	 */
	@Override
	public void addUserToProxy(Long parendId, ShopUser user) {
		DFUserRelation df = new DFUserRelation();
		df.setChild(user);
		df.setParent(shopUserRepository.findOne(parendId));
		df.setCreateDate(new Date());
		df.setStatus(1);
		df.setDesc("");
		df.setAllProductCount(0);
		df.setHasProductCount(0);
		df.setType(0);
		dFUserRelationRepository.save(df);
		DFUserRelation df1 = new DFUserRelation();
		df1.setChild(shopUserRepository.findOne(parendId));
		df1.setParent(user);
		df1.setCreateDate(new Date());
		df1.setStatus(1);
		df1.setDesc("");
		df1.setAllProductCount(0);
		df1.setHasProductCount(0);
		df1.setType(1);
		dFUserRelationRepository.save(df1);
	}
	/**
	 * 代理删除分销商
	 */
	@Override
	public void deleteById(Long id) {
		//代理删除分销商时候同时删除分销产品表
		DFUserRelation dfUF = dFUserRelationRepository.findOne(id);
		List<DFUserRelation> dfUFList = dFUserRelationRepository.getByPartentAndChild(dfUF.getChild().getId(), dfUF.getParent().getId());
		//删除好友之间产品关系
		List<FProduct> list = fProductRepository.getByModifyUserIdAndCreateUserId(dfUF.getChild().getId(), dfUF.getParent().getId());
		fProductRepository.delete(list);
		//删除可见产品关系
		fShowProductRepository.deleteByUserId(dfUF.getParent().getId());
		for(DFUserRelation df:dfUFList){
			List<FProduct> list1 = fProductRepository.getByModifyUserIdAndCreateUserId(df.getChild().getId(), df.getParent().getId());
			fProductRepository.delete(list1);
			//删除可见产品关系
			fShowProductRepository.deleteByUserId(df.getParent().getId());
		}
		dFUserRelationRepository.delete(dfUFList);
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
	@Override
	public List<DFUserRelation> getByProperty(Long parentId, Long childId) {
		return dFUserRelationRepository.getByProperty(parentId, childId);
	}
	
	@Override
	public List<DFUserRelation> getByParentId(Long parentId) {
		return dFUserRelationRepository.getByParentId(parentId);
	}
	@Override
	public int queryHasFProductById(Long modifyUserId, Long createUserId) {
		return dFUserRelationRepository.queryHasFProductById(modifyUserId,createUserId);
	}
	@Override
	public boolean updateYwDesc(String ywDesc, Long id) {
		try{
			dFUserRelationRepository.updateDFUserRelationYwDesc(ywDesc, id);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * 统一好友申请
	 */
	@Override
	public boolean agreeApply(Long id) {
		try{
			DFUserRelation dfUF = dFUserRelationRepository.findOne(id);
			List<DFUserRelation> dfUFList = dFUserRelationRepository.getByPartentAndChild(dfUF.getChild().getId(), dfUF.getParent().getId());
			//更新好友关系表
			for(DFUserRelation list:dfUFList){
				list.setStatus(2);
				dFUserRelationRepository.merge(list);
				//在代理添加业务的时候，将代理的所有产品添加到业务
				addAllProductToProxy(list.getParent(),list.getChild());
			}
			dfUF.setStatus(2);
			dFUserRelationRepository.merge(dfUF);
			//在代理添加业务的时候，将代理的所有产品添加到业务
			addAllProductToProxy(dfUF.getParent(),dfUF.getChild());
			return true;
		}catch(Exception e){
			return false;
		}
		
	}
	
	/**
	 * 增加好友之间可见产品
	 */
	public void addAllProductToProxy(ShopUser parentUser, ShopUser shopUser) {
		List<DProduct> dpList = dProductRepository.getAllByUserId(parentUser.getId());
		for(DProduct list:dpList){
			FShowProduct fsp = new FShowProduct();
			fsp.setCreateDate(new Date());
			fsp.setUser(shopUser);
			fsp.setDproduct(list);
			fShowProductRepository.save(fsp);
			FProduct fProduct = new FProduct();
			fProduct.setpName(list.getpName());
			fProduct.setdProduct(list);
			fProduct.setDesc(list.getDesc());
			fProduct.setKindId(list.getKindId());
			fProduct.setShowKind(list.getShowKind());
			fProduct.setCreateTime(new Date());
			fProduct.setCreateUserId(parentUser);
			fProduct.setPrice(list.getPrice());
			fProduct.setModifyUserId(shopUser);
			fProduct.setChangeFlag(0);
			fProduct.setDeleteFlag(0);
			fProduct.setStatus(list.getStatus());
			fProduct.setOnSale(2);
			fProduct = fProductRepository.save(fProduct);
			//添加图片
			// 获取代理该产品下的所有图片
			List<ProductPic> picList = productPicRepository.getPicByDProductId(list.getId());
			savePics(picList, fProduct);
		}
	}
	/**
	 * 保存pic
	 * @param picList
	 * @param fProduct
	 */
	private void savePics(List<ProductPic> picList, FProduct fProduct){
		if(picList.size() > 0){
			for (int i = 0; i < picList.size(); i++){
				ProductPic productPic = picList.get(i);
				ProductPic productPicNew = new ProductPic();
				productPicNew.setCreateDate(new Date());
				productPicNew.setImageUrl(productPic.getImageUrl());
				productPicNew.setThumbUrl(productPic.getThumbUrl());
				productPicNew.setFproduct(fProduct);
				productPicRepository.save(productPicNew);
			}
		}
	}

}
