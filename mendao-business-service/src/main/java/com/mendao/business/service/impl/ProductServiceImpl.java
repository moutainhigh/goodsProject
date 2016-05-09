package com.mendao.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.FProduct;
import com.mendao.business.repository.DProductRepository;
import com.mendao.business.repository.FProductRepository;
import com.mendao.business.service.ProductService;
import com.mendao.framework.base.jpa.PageEntity;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	DProductRepository dProductRepository;
	
	@Autowired
	FProductRepository fProductRepository;
	
	@Override
	public PageEntity<DProduct> getDProductPage(PageEntity<DProduct> pageEntity) {
		return dProductRepository.findByPage(pageEntity);
	}

	@Override
	public PageEntity<FProduct> getFProductPage(PageEntity<FProduct> pageEntity) {
		return fProductRepository.findByPage(pageEntity);
	}

}
