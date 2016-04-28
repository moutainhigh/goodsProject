package com.mendao.business.service;


import com.mendao.business.entity.SchoolProduct;
import com.mendao.framework.base.jpa.PageEntity;

public interface SchoolProductService {

	PageEntity<SchoolProduct> getPage(PageEntity<SchoolProduct> pageEntity);

	void save(SchoolProduct product);

	SchoolProduct findById(Long id);

	void deleteById(Long id);
	
	

}
