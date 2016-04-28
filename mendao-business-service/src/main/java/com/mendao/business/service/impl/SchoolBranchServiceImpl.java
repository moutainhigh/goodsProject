package com.mendao.business.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.BranchSchool;
import com.mendao.business.repository.BranchSchoolRepository;
import com.mendao.business.service.SchoolBranchService;
import com.mendao.framework.base.jpa.PageEntity;

@Service("schoolBranchService")
public class SchoolBranchServiceImpl implements SchoolBranchService{

	@Autowired
	private BranchSchoolRepository branchSchoolRepository;
	/**
	 * 学校课程分页数据
	 */
	@Override
	public PageEntity<BranchSchool> getPage(PageEntity<BranchSchool> pageEntity) {
		
		return branchSchoolRepository.findByPage(pageEntity);
	}
	/**
	 * 保存学校课程
	 */
	@Override
	public void save(BranchSchool branch) {
		branchSchoolRepository.save(branch);
	}
	/**
	 * 根据ID查找学校课程
	 */
	@Override
	public BranchSchool findById(Long id) {
		return branchSchoolRepository.findOne(id);
	}
	/**
	 * 根据ID删除学校课程
	 */
	@Override
	public void deleteById(Long id) {
		branchSchoolRepository.delete(id);
	}

}
