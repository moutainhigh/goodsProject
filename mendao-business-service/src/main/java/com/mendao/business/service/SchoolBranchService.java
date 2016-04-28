package com.mendao.business.service;


import com.mendao.business.entity.BranchSchool;
import com.mendao.framework.base.jpa.PageEntity;

public interface SchoolBranchService {

	PageEntity<BranchSchool> getPage(PageEntity<BranchSchool> pageEntity);

	void save(BranchSchool branch);

	BranchSchool findById(Long id);

	void deleteById(Long id);
	
	

}
