package com.mendao.business.service;


import java.util.List;

import com.mendao.business.entity.School;
import com.mendao.framework.base.jpa.PageEntity;

public interface SchoolService extends BaseService<School, Long>{
	 public PageEntity<School> getPage(PageEntity<School> myPage);

	 public void save(School school);
	 
	 public void update(School school);
	public List<School> findListByIds(String[] ids);

	public PageEntity<School> getSchoolPage(PageEntity<School> pageEntity);
}
