package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.ProductPic;


public interface ProductPicService {

	void addProductPic(List<ProductPic> list);

	List<ProductPic> getPicByDProductId(Long id);

	List<ProductPic> getPicByFProductId(Long id);

	
}
