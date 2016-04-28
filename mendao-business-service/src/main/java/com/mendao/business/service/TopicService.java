package com.mendao.business.service;


import java.util.List;

import com.mendao.business.entity.Topic;
import com.mendao.framework.base.jpa.PageEntity;

public interface TopicService{
	 public PageEntity<Topic> getPage(PageEntity<Topic> myPage);

	 public void save(Topic topic);

	public Topic findById(Long id);

	public List<Topic> findListByIds(String[] ids);

	List<Topic> findList();

	PageEntity<Topic> getPageUsingProcedure(PageEntity<Topic> myPage);

	public List<Topic> findRelationTopic(PageEntity<Topic> myPage);
}
