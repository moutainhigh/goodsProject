package com.mendao.business.service;



import java.util.List;

import com.mendao.business.entity.FreeStudy;
import com.mendao.framework.base.jpa.PageEntity;

public interface FreeStudyService{
	public PageEntity<FreeStudy> getPage(PageEntity<FreeStudy> myPage);

	public void save(FreeStudy freeStudy);

	public FreeStudy findById(Long id);

	public List<FreeStudy> findListByIds(String[] ids);

}
