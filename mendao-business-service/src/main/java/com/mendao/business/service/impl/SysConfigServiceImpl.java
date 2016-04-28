package com.mendao.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.SysDictionary;
import com.mendao.business.repository.SysDictionaryRepository;
import com.mendao.business.service.SysConfigService;
import com.mendao.framework.base.jpa.PageEntity;

@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService{

	@Autowired
	SysDictionaryRepository dicRep;
	
	@Override
	public void editDictionary(SysDictionary dic){
		dicRep.save(dic);
	}
	
	@Override
	public void deleteDictionary(Long id){
		dicRep.delete(id);
	}
	
	@Override
	public PageEntity<SysDictionary> getPageOfDictionary(PageEntity<SysDictionary> pageBean){
		
		return dicRep.findByPage(pageBean);
	}
	
	@Override
	public List<SysDictionary> findListByCode(String code){
		return dicRep.findListByCode(code);
	}

	@Override
	public SysDictionary findById(Long id) {
		return dicRep.findOne(id);
	}
}
