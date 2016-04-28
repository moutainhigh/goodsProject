package com.mendao.framework.service;

import java.util.List;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.FwSource;

public interface SourceService  {
	public FwSource findOne(Long id);
	public void saveSource(FwSource source);
	public List<FwSource> getMenuSources();
	public List<FwSource> getAllSourceDataAsTree();
	PageEntity<FwSource> findPage(PageEntity<FwSource> pageBean);
}
