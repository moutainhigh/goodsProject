package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.SysDictionary;
import com.mendao.framework.base.jpa.PageEntity;

public interface SysConfigService {

	void editDictionary(SysDictionary dic);

	void deleteDictionary(Long id);

	PageEntity<SysDictionary> getPageOfDictionary(PageEntity<SysDictionary> pageBean);

	List<SysDictionary> findListByCode(String code);

	SysDictionary findById(Long id);
}
