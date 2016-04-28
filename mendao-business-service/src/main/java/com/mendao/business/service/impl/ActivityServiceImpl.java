package com.mendao.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.Activity;
import com.mendao.business.repository.ActivityRepository;
import com.mendao.business.service.ActivityService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.util.Util;

/**
 * 活动service实现类
 * @author warden
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService{

	@Autowired
	private ActivityRepository activityRep;
	/**
	 * 获取活动列表分页
	 */
	@Override
	public PageEntity<Activity> getPage(PageEntity<Activity> myPage) {
		return activityRep.findByPage(myPage);
	}
	/**
	 * 保存活动
	 */
	@Override
	public void save(Activity activity) {
		activityRep.save(activity);
	}
	/**
	 * 根据ID查找活动
	 */
	@Override
	public Activity findById(Long id) {
		return activityRep.findOne(id);
	}
	/**
	 * 根据ids查找活动
	 */
	@Override
	public List<Activity> findListByIds(String[] ids) {
		String param =Util.getIdSQLParam(ids);
		if (param != null) {
			return activityRep.findListByHql("from Activity t where t.id in (" + param + ")");
		}
		return null;
	}

}
