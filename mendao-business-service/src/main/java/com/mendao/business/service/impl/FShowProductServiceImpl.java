package com.mendao.business.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.FProduct;
import com.mendao.business.entity.FShowProduct;
import com.mendao.business.entity.ProductPic;
import com.mendao.business.repository.DProductRepository;
import com.mendao.business.repository.FProductRepository;
import com.mendao.business.repository.FShowProductRepository;
import com.mendao.business.repository.ProductPicRepository;
import com.mendao.business.service.FShowProductService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.ShopUser;

@Service
public class FShowProductServiceImpl implements FShowProductService{

	@Autowired
	private FShowProductRepository fShowProductRepository;
	
	@Autowired
	private DProductRepository dProductRepository;
	
	@Autowired
	private FProductRepository fProductRepository;
	
	@Autowired
	private ProductPicRepository productPicRepository;
	
	
	@Override
	public PageEntity<FShowProduct> getProductPage(PageEntity<FShowProduct> pageEntity) {
		return fShowProductRepository.findByPage(pageEntity);
	}


	@Override
	@Transactional
	public void deleteById(Long userId, Long id) {
		FShowProduct fShowProduct = fShowProductRepository.findOne(id);
		fShowProduct.getDproduct().getId();
		FProduct fp = fProductRepository.getByProperty(fShowProduct.getUser().getId(),userId,fShowProduct.getDproduct().getId());
		if(fp != null){
			//删除分销产品
			fProductRepository.delete(fp);
		}
		//删除分销可见记录
		fShowProductRepository.delete(id);
	}


	@Override
	@Transactional
	public void addProductToProxy(ShopUser dUser, ShopUser proxyUser, String ids) {
//		//删除该业务代理代理的所有的产品
//		String hql = "select * from t_f_product where create_user_id="+dUser.getId()+" and modify_user_id="+proxyUser.getId()+" and d_product not in ("+ids+")";
//		List<FProduct> fpList = fProductRepository.findAllBySql(FProduct.class, hql);
//		if(fpList != null && fpList.size() > 0){
//			fProductRepository.delete(fpList);
//		}
//		//先删除原来保存的产品关系
//		String showHql = "select * from t_f_show_product where user_id = "+proxyUser.getId()+" and dproduct_id not in ("+ids+")";
//		List<FShowProduct> fspList= fShowProductRepository.findAllBySql(FShowProduct.class, showHql);
//		if(fspList != null && fspList.size() > 0){
//			fShowProductRepository.delete(fspList);
//		}
		
		String[] array = ids.split(",");
		for(int i=0;i<array.length;i++){
			//先查找此商品是否再可见商品中
			String showHql = "select * from t_f_show_product where user_id = "+proxyUser.getId()+" and dproduct_id = "+array[i];
			List<FShowProduct> fspList= fShowProductRepository.findAllBySql(FShowProduct.class, showHql);
			if(fspList == null || fspList.size() == 0){
				FShowProduct fsp = new FShowProduct();
				fsp.setCreateDate(new Date());
				fsp.setUser(proxyUser);
				DProduct dProduct = dProductRepository.findOne(Long.valueOf(array[i]));
				fsp.setDproduct(dProduct);
				fShowProductRepository.save(fsp);
				FProduct fProduct = new FProduct();
				fProduct.setpName(dProduct.getpName());
				fProduct.setdProduct(dProduct);
				fProduct.setDesc(dProduct.getDesc());
				fProduct.setKindId(dProduct.getKindId());
				fProduct.setShowKind(dProduct.getShowKind());
				fProduct.setCreateTime(new Date());
				fProduct.setCreateUserId(dUser);
				fProduct.setPrice(dProduct.getPrice());
				fProduct.setModifyUserId(proxyUser);
				fProduct.setChangeFlag(0);
				fProduct.setDeleteFlag(0);
				fProduct.setStatus(dProduct.getStatus());
				fProduct.setOnSale(2);
				fProduct = fProductRepository.save(fProduct);
				//添加图片
				// 获取代理该产品下的所有图片
				List<ProductPic> picList = productPicRepository.getPicByDProductId(Long.valueOf(array[i]));
				savePics(picList, fProduct);
			}
		}
	}

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

	@Override
	public List<Long> getDProductByUserId(Long proxyId) {
		return fShowProductRepository.getDProductByUserId(proxyId);
	}

	/**
	 * 将代理的所有产品添加到业务
	 */
	@Override
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


	@Override
	public void addProductToAllProxy(ShopUser child, DProduct dProduct) {
		FShowProduct fsp = new FShowProduct();
		fsp.setCreateDate(new Date());
		fsp.setUser(child);
		fsp.setDproduct(dProduct);
		fShowProductRepository.save(fsp);
		FProduct fProduct = new FProduct();
		fProduct.setpName(dProduct.getpName());
		fProduct.setdProduct(dProduct);
		fProduct.setDesc(dProduct.getDesc());
		fProduct.setKindId(dProduct.getKindId());
		fProduct.setShowKind(dProduct.getShowKind());
		fProduct.setCreateTime(new Date());
		fProduct.setCreateUserId(dProduct.getCreateUserId());
		fProduct.setPrice(dProduct.getPrice());
		fProduct.setModifyUserId(child);
		fProduct.setChangeFlag(0);
		fProduct.setDeleteFlag(0);
		//默认代理产品是下架的
		fProduct.setStatus(0);
		fProduct.setOnSale(2);
		fProduct = fProductRepository.save(fProduct);
		//添加图片
		// 获取代理该产品下的所有图片
		List<ProductPic> picList = productPicRepository.getPicByDProductId(dProduct.getId());
		savePics(picList, fProduct);
	}


	@Override
	public void deleteProductToProxy(ShopUser dUser, ShopUser proxyUser,String ids) {
		//删除该好友代理代理的所有的产品
		String hql = "select * from t_f_product where create_user_id="+dUser.getId()+" and modify_user_id="+proxyUser.getId()+" and d_product in ("+ids+")";
		List<FProduct> fpList = fProductRepository.findAllBySql(FProduct.class, hql);
		if(fpList != null && fpList.size() > 0){
			fProductRepository.delete(fpList);
		}
		//删除好友原来保存的产品关系
		String showHql = "select * from t_f_show_product where user_id = "+proxyUser.getId()+" and dproduct_id in ("+ids+")";
		List<FShowProduct> fspList= fShowProductRepository.findAllBySql(FShowProduct.class, showHql);
		if(fspList != null && fspList.size() > 0){
			fShowProductRepository.delete(fspList);
		}
	}


	@Override
	public void addMyProduct(ShopUser shopUser, DProduct dProduct) {
		FProduct fProduct = new FProduct();
//		BeanUtils.copyProperties(dProduct, fProduct);
		fProduct.setpName(dProduct.getpName());
		fProduct.setPrice(dProduct.getPrice());
		fProduct.setDesc(dProduct.getDesc());
		fProduct.setKindId(dProduct.getKindId());
		fProduct.setShowKind(dProduct.getShowKind());
		fProduct.setOther(dProduct.getOther());
		fProduct.setStatus(dProduct.getStatus());
		fProduct.setVideoUrl(dProduct.getVideoUrl());
		fProduct.setDeleteFlag(dProduct.getDeleteFlag());
		
		fProduct.setModifyUserId(shopUser);
		fProduct.setOnSale(dProduct.getStatus());
		fProduct.setType(1);
		fProduct.setCreateTime(new Date());
		fProduct.setChangeFlag(1);
		fProduct.setCreateUserId(shopUser);
		fProduct.setdProduct(dProduct);
		fProduct = fProductRepository.save(fProduct);
		//添加图片
		// 获取代理该产品下的所有图片
		List<ProductPic> picList = productPicRepository.getPicByDProductId(dProduct.getId());
		savePics(picList, fProduct);
	}


	@Override
	public void updateMyProduct(ShopUser shopUser, DProduct dProduct) {
		FProduct fProduct = fProductRepository.getByProperty(shopUser.getId(), shopUser.getId(), dProduct.getId());
		if(fProduct != null){
			//删除原有的产品图片关系
			productPicRepository.deleteByFproductId(fProduct.getId());
			//先删除后增加
			fProductRepository.delete(fProduct);
		}
		fProduct = new FProduct();
//		BeanUtils.copyProperties(dProduct, fProduct);
		fProduct.setpName(dProduct.getpName());
		fProduct.setPrice(dProduct.getPrice());
		fProduct.setDesc(dProduct.getDesc());
		fProduct.setKindId(dProduct.getKindId());
		fProduct.setShowKind(dProduct.getShowKind());
		fProduct.setOther(dProduct.getOther());
		fProduct.setStatus(dProduct.getStatus());
		fProduct.setVideoUrl(dProduct.getVideoUrl());
		fProduct.setDeleteFlag(dProduct.getDeleteFlag());
		
		
		fProduct.setModifyUserId(shopUser);
		fProduct.setOnSale(dProduct.getStatus());
		fProduct.setType(1);
		fProduct.setCreateTime(new Date());
		fProduct.setChangeFlag(1);
		fProduct.setCreateUserId(shopUser);
		fProduct.setdProduct(dProduct);
		fProduct = fProductRepository.save(fProduct);
		
		// 获取代理该产品下的所有图片
		List<ProductPic> picList = productPicRepository.getPicByDProductId(dProduct.getId());
		savePics(picList, fProduct);
	}

	
}
