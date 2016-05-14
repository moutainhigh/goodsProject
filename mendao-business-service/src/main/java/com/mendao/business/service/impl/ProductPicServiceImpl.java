package com.mendao.business.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mendao.business.entity.ProductPic;
import com.mendao.business.repository.ProductPicRepository;
import com.mendao.business.service.ProductPicService;

@Service
public class ProductPicServiceImpl implements ProductPicService{

	@Autowired
	private ProductPicRepository productPicRepository;

	@Override
	public void addProductPic(List<ProductPic> list) {
		//保存产品图片之前先删除以前保存的图片
		productPicRepository.deletePicByDProductId(list.get(0).getDproduct().getId());
		productPicRepository.save(list);
	}

	@Override
	public List<ProductPic> getPicByDProductId(Long id) {
		return productPicRepository.getPicByDProductId(id);
	}
	
	
}
