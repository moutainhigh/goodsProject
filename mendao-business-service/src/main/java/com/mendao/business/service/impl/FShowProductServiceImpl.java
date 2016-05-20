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
			//删除附件图片
			productPicRepository.deleteByFproductId(fp.getId());
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
			fProduct.setOnSale(0);
			fProduct = fProductRepository.save(fProduct);
			//设置分销产品图片
			List<ProductPic> ppList = productPicRepository.getPicByDProductId(dProduct.getId());
			for(ProductPic pic:ppList){
				ProductPic productPic = new ProductPic();
				productPic.setFproduct(fProduct);
				productPic.setImageUrl(pic.getImageUrl());
				productPic.setThumbUrl(pic.getThumbUrl());
				productPic.setCreateDate(new Date());
				productPicRepository.save(productPic);
			}
		}
	}


	@Override
	public List<Long> getDProductByUserId(Long proxyId) {
		return fShowProductRepository.getDProductByUserId(proxyId);
	}

	
}
