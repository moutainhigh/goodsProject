package com.mendao.business.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.Advert;
import com.mendao.business.repository.AdvertRepository;
import com.mendao.business.service.AdvertService;
import com.mendao.framework.base.jpa.PageEntity;

@Service("advertService")
public class AdvertServiceImpl implements AdvertService{

	@Autowired
	private AdvertRepository advertRepository;
	/**
	 * 标签分页数据
	 */
	@Override
	public PageEntity<Advert> getPage(PageEntity<Advert> pageEntity) {
		
		return advertRepository.findByPage(pageEntity);
	}
	/**
	 * 保存标签
	 */
	@Override
	public void save(Advert advert) {
		advertRepository.save(advert);
	}
	/**
	 * 根据ID查找标签
	 */
	@Override
	public Advert findById(Long id) {
		return advertRepository.findOne(id);
	}
	/**
	 * 根据ID删除标签
	 */
	@Override
	public void deleteById(Long id) {
		advertRepository.delete(id);
	}
	/**
	 * 根据位置查找广告
	 */
	@Override
	public List<Advert> getListByPlace(String place) {
		return advertRepository.getListByPlace(place);
	}

}
