package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.TopicCollection;
import com.mendao.framework.base.jpa.PageEntity;

public interface TopicCollectionService {
	
	public PageEntity<TopicCollection> getPage(PageEntity<TopicCollection> myPage);

	public void save(TopicCollection topicCollection);

	public TopicCollection findById(Long id);

	public List<TopicCollection> findByAttributes(Long aothorId, Long topicId);
}
