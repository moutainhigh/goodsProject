package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.ActivityComment;
import com.mendao.framework.base.jpa.PageEntity;

public interface ActivityCommentService {
	public PageEntity<ActivityComment> getPage(PageEntity<ActivityComment> myPage);

	public void save(ActivityComment activityComment);

	public ActivityComment findById(Long id);

	public List<ActivityComment> getCommentByParentId(Long id);
}
