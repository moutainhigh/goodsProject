package com.mendao.business.service;



import java.util.List;

import com.mendao.business.entity.Activity;
import com.mendao.framework.base.jpa.PageEntity;

public interface ActivityService{
	 public PageEntity<Activity> getPage(PageEntity<Activity> myPage);

	 public void save(Activity activity);

	public Activity findById(Long id);

	public List<Activity> findListByIds(String[] ids);

}
