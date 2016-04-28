package com.mendao.business.service;


import java.util.List;

import com.mendao.business.entity.ActivityUser;
import com.mendao.framework.base.jpa.PageEntity;

public interface ActivityUserService{
	 public PageEntity<ActivityUser> getPage(PageEntity<ActivityUser> myPage);

	 public void save(ActivityUser activityUser);

	public ActivityUser findById(Long id);

	public List<ActivityUser> findByAttributes(Long userId, Long activityId);
}
