package com.mendao.business.service.impl;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.mendao.business.service.BaseService;
import com.mendao.framework.base.jpa.BaseRepository;

public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID>{

	@Autowired
	BaseRepository<T,ID> baseRepository;
	
	@Override
	public T findOne(ID id) {
		return baseRepository.findOne(id);
	}

	@Override
	@Transactional
	public T saveBean(T bean){
		baseRepository.save(bean);
		return bean;
	}
}
