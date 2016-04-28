package com.mendao.business.service;


import java.util.List;

import com.mendao.business.entity.TopicComment;
import com.mendao.framework.base.jpa.PageEntity;

public interface TopicCommentService{
	 public PageEntity<TopicComment> getPage(PageEntity<TopicComment> myPage);

	 public void save(TopicComment topicComment);

	public TopicComment findById(Long id);

	public List<TopicComment> getCommentByParentId(Long id);
}
