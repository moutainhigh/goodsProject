package com.mendao.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.DProduct;
import com.mendao.business.entity.FProduct;
import com.mendao.business.entity.PKind;
import com.mendao.business.repository.DProductRepository;
import com.mendao.business.repository.FProductRepository;
import com.mendao.business.repository.PKindRepository;
import com.mendao.business.service.ProductService;
import com.mendao.framework.base.jpa.PageEntity;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	DProductRepository dProductRepository;
	
	@Autowired
	FProductRepository fProductRepository;
	
	@Autowired
	PKindRepository pKindRespository;
	/**
	 * 产品列表 代理
	 */
	@Override
	public PageEntity<DProduct> getDProductPage(PageEntity<DProduct> pageEntity) {
		return dProductRepository.findByPage(pageEntity);
	}
	
	/**
	 * 添加代理产品
	 */
	public DProduct addDProduct(DProduct dProduct){
		return dProductRepository.save(dProduct);
	}
	
	/**
	 * 通过id查找代理产品
	 */
	public DProduct findDProductById(Long id){
		return dProductRepository.findOne(id);
	}
	
	/**
	 * 修改代理产品
	 */
	public void updateDProduct(DProduct dProduct){
		dProductRepository.merge(dProduct);
	}
	
	/**
	 * 产品列表 分销
	 */
	@Override
	public PageEntity<FProduct> getFProductPage(PageEntity<FProduct> pageEntity) {
		return fProductRepository.findByPage(pageEntity);
	}

	/**
	 * 查询类目
	 */
	@Override
	public PageEntity<PKind> getPKindPage(PageEntity<PKind> pageEntity) {
		return pKindRespository.findByPage(pageEntity);
	}
	/**
	 * 添加类目
	 */
	@Override
	public PKind addPKind(PKind pKind) {
		return pKindRespository.save(pKind);
	}
	/**
	 * 通过id查找类目
	 */
	@Override
	public PKind findById(Long id) {
		return pKindRespository.findOne(id);
	}
	
	/**
	 * 修改类目
	 */
	@Override
	public void update(PKind pKind) {
		pKindRespository.merge(pKind);;
	}
	/**
	 * 删除类目
	 */
	@Override
	public void deletePKindById(Long id) {
		pKindRespository.delete(id);
	}
	
	/**
	 * 查询所有的类目
	 */
	public List<PKind> queryAllPropertiesByCreateId(Long id){
		String hql = "select id, kind_name, create_id, parent_id, status from t_kind where create_id = " + id;
		return pKindRespository.findAllBySql(PKind.class, hql);
	}
}
