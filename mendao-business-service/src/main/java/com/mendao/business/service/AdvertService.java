package com.mendao.business.service;


import java.util.List;

import com.mendao.business.entity.Advert;
import com.mendao.framework.base.jpa.PageEntity;

public interface AdvertService {

	PageEntity<Advert> getPage(PageEntity<Advert> pageEntity);

	void save(Advert advert);

	Advert findById(Long id);

	void deleteById(Long id);
	
	List<Advert> getListByPlace(String place);
	

}
