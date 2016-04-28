package com.mendao.business.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.SchoolProduct;
import com.mendao.business.repository.SchoolProductRepository;
import com.mendao.business.service.SchoolProductService;
import com.mendao.framework.base.jpa.PageEntity;

@Service("schoolProductService")
public class SchoolProductServiceImpl implements SchoolProductService{

	@Autowired
	private SchoolProductRepository schoolProductRepository;
	/**
	 * 学校课程分页数据
	 */
	@Override
	public PageEntity<SchoolProduct> getPage(PageEntity<SchoolProduct> pageEntity) {
		
		return schoolProductRepository.findByPage(pageEntity);
	}
	/**
	 * 保存学校课程
	 */
	@Override
	public void save(SchoolProduct attachment) {
		schoolProductRepository.save(attachment);
	}
	/**
	 * 根据ID查找学校课程
	 */
	@Override
	public SchoolProduct findById(Long id) {
		return schoolProductRepository.findOne(id);
	}
	/**
	 * 根据ID删除学校课程
	 */
	@Override
	public void deleteById(Long id) {
		schoolProductRepository.delete(id);
	}

}
