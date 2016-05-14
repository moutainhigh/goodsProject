package com.mendao.business.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.DFUserRelation;
import com.mendao.business.entity.FShowProduct;
import com.mendao.business.repository.DProductRepository;
import com.mendao.business.repository.FShowProductRepository;
import com.mendao.business.service.FShowProductService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.ShopUser;

@Service
public class FShowProductServiceImpl implements FShowProductService{

	@Autowired
	private FShowProductRepository fShowProductRepository;
	
	@Autowired
	private DProductRepository dProductRepository;
	
	
	@Override
	public PageEntity<FShowProduct> getProductPage(PageEntity<FShowProduct> pageEntity) {
		return fShowProductRepository.findByPage(pageEntity);
	}


	@Override
	public void deleteById(Long id) {
		fShowProductRepository.delete(id);
	}


	@Override
	public void addProductToProxy(ShopUser proxyUser, String ids) {
		String[] array = ids.split(",");
		for(int i=0;i<array.length;i++){
			FShowProduct fsp = new FShowProduct();
			fsp.setCreateDate(new Date());
			fsp.setUser(proxyUser);
			fsp.setDproduct(dProductRepository.findOne(Long.valueOf(array[i])));
			fShowProductRepository.save(fsp);
		}
	}


	@Override
	public List<Long> getDProductByUserId(Long proxyId) {
		return fShowProductRepository.getDProductByUserId(proxyId);
	}

	
}
