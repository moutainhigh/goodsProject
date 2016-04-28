package com.mendao.business.service;


import java.util.List;

import com.mendao.business.entity.Recommend;
import com.mendao.business.entity.School;
import com.mendao.framework.base.jpa.PageEntity;

public interface RecommendService{
	 public PageEntity<Recommend> getPage(PageEntity<Recommend> myPage);

	 public void save(Recommend recommend);

	public Recommend findById(Long id);

	public List<Recommend> findListByIds(String[] ids);

	public void deleteAll(List<Recommend> recommends);

	public List<Recommend> getIdListByType(int type);

	public List<School> getSchoolListByAttributes(int type, Long categoryId);
}
