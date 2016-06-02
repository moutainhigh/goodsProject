package com.mendao.business.service.impl;


import java.util.Date;
import java.util.List;

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
		String[] array = ids.split(",");
		for(int i=0;i<array.length;i++){
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
			fProduct.setCreateTime(new Date());
			fProduct.setCreateUserId(dUser);
			fProduct.setPrice(dProduct.getPrice());
			fProduct.setModifyUserId(proxyUser);
			fProduct.setChangeFlag(0);
			fProduct.setDeleteFlag(0);
			fProduct.setStatus(dProduct.getStatus());
			fProduct.setOnSale(1);
			fProduct = fProductRepository.save(fProduct);
			//添加图片
			// 获取代理该产品下的所有图片
			List<ProductPic> picList = productPicRepository.getPicByDProductId(Long.valueOf(array[i]));
			savePics(picList, fProduct);
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
			fProduct.setCreateTime(new Date());
			fProduct.setCreateUserId(parentUser);
			fProduct.setPrice(list.getPrice());
			fProduct.setModifyUserId(shopUser);
			fProduct.setChangeFlag(0);
			fProduct.setDeleteFlag(0);
			fProduct.setStatus(list.getStatus());
			fProduct.setOnSale(1);
			fProduct = fProductRepository.save(fProduct);
		}
	}

	
}
