package com.mendao.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.FProduct;
import com.mendao.business.entity.PKind;
import com.mendao.business.repository.DProductRepository;
import com.mendao.business.repository.FProductRepository;
import com.mendao.business.repository.FShowProductRepository;
import com.mendao.business.repository.PKindRepository;
import com.mendao.business.repository.ProductPicRepository;
import com.mendao.business.service.ProductService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.ShopUser;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	DProductRepository dProductRepository;
	
	@Autowired
	FProductRepository fProductRepository;
	
	@Autowired
	PKindRepository pKindRespository;
	
	@Autowired
	FShowProductRepository fShowProductRepository;
	
	@Autowired
	ProductPicRepository productPicRepository;
	/**
	 * 产品列表 代理
	 */
	@Override
	public PageEntity<DProduct> getDProductPage(PageEntity<DProduct> pageEntity) {
		return dProductRepository.findByPage(pageEntity);
	}
	
	/**
	 * 添加代理产品
	 */
	public DProduct addDProduct(DProduct dProduct){
		return dProductRepository.save(dProduct);
	}
	
	/**
	 * 通过id查找代理产品
	 */
	public DProduct findDProductById(Long id){
		return dProductRepository.findOne(id);
	}
	
	/**
	 * 修改代理产品
	 */
	public void updateDProduct(DProduct dProduct){
		dProductRepository.merge(dProduct);
	}
	
	/**
	 * 修改分销产品
	 */
	public void updateFProduct(FProduct fProduct){
		fProductRepository.merge(fProduct);
	}
	/**
	 * 删除产品 代理
	 */
	@Override
	public void deleteDProductById(Long id) {
		//删除分销图片
		productPicRepository.deletePicByDProductId1(id);
		//删除分销产品
		fProductRepository.deleteFProductByDProductId(id);
		//删除可见关联
		fShowProductRepository.deleteByDProductId(id);
		//代理产品删除图片
		productPicRepository.deletePicByDProductId(id);
		//删除代理产品
		dProductRepository.deleteDProductById(id);
	}
	
	public void deleteFProductById(Long id){
		//删除代理产品(物理删除)
		fProductRepository.delete(id);
	}
	
	/**
	 * 产品列表 分销
	 */
	@Override
	public PageEntity<FProduct> getFProductPage(PageEntity<FProduct> pageEntity) {
		return fProductRepository.findByPage(pageEntity);
	}

	/**
	 * 查询类目
	 */
	@Override
	public PageEntity<PKind> getPKindPage(PageEntity<PKind> pageEntity) {
		return pKindRespository.findByPage(pageEntity);
	}
	/**
	 * 添加类目
	 */
	@Override
	public PKind addPKind(PKind pKind) {
		return pKindRespository.save(pKind);
	}
	/**
	 * 通过id查找类目
	 */
	@Override
	public PKind findById(Long id) {
		return pKindRespository.findOne(id);
	}
	
	/**
	 * 修改类目
	 */
	@Override
	public void update(PKind pKind) {
		pKindRespository.merge(pKind);;
	}
	/**
	 * 删除类目
	 */
	@Override
	public void deletePKindById(Long id) {
		pKindRespository.delete(id);
	}
	
	/**
	 * 查询所有的类目
	 */
	public List<PKind> queryAllPropertiesByCreateId(Long id){
		String hql = "select id, kind_name, create_id, parent_id, status from t_kind where create_id = " + id;
		return pKindRespository.findAllBySql(PKind.class, hql);
	}

	/**
	 * 批量修改产品状态
	 */
	@Override
	public void updateProductStatus(Integer status,String ids) {
		String[] idString = ids.split(",");
		List<Long> idsList = new ArrayList<Long>();
		for(int i = 0; i < idString.length; i++){
			idsList.add(Long.parseLong(idString[i]));
		}
		dProductRepository.updateProductStatusByIds(status, idsList);
	}

	@Override
	public List<ShopUser> getAllDaiLiByCurrentUserId(Long id) {
		return fProductRepository.queryDailiByFenxiao(id);
	}

	@Override
	public boolean updateFProductOnSale(Integer onSale, String ids) {
		try{
			if(ids.length() > 0){
				String[] idsArray = ids.split(",");
				List<Long> idsList = new ArrayList<Long>();
				for(int i = 0; i < idsArray.length; i++){
					idsList.add(Long.parseLong(idsArray[i]));
				}
				fProductRepository.updateFProductOnSaleByIds(onSale, idsList);
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public FProduct getDProductById(Long id) {
		return fProductRepository.findOne(id);
	}

	@Override
	public List<FProduct> getByModifyUserId(Long modifyUserId, Long id,int limit) {
		return fProductRepository.getByModifyUserId(modifyUserId, id);
	}

	@Override
	public List<PKind> queryAllByYewuId(Long id) {
		String hql = "select id, kind_name, create_id, parent_id, status from t_kind where create_id = (select parent_id from t_user_relation where child_id = "+id+")" ;
		return pKindRespository.findAllBySql(PKind.class, hql);
	}
}
