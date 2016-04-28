package com.mendao.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.ActivityUser;
import com.mendao.business.repository.ActivityUserRepository;
import com.mendao.business.service.ActivityUserService;
import com.mendao.framework.base.jpa.PageEntity;

/**
 * 活动用户service实现类
 * @author warden
 */
@Service("activityUserService")
public class ActivityUserServiceImpl implements ActivityUserService{

	@Autowired
	private ActivityUserRepository activityUserRep;
	/**
	 * 获取活动列表分页
	 */
	@Override
	public PageEntity<ActivityUser> getPage(PageEntity<ActivityUser> myPage) {
		return activityUserRep.findByPage(myPage);
	}
	/**
	 * 保存活动
	 */
	@Override
	public void save(ActivityUser activityUser) {
		activityUserRep.save(activityUser);
	}
	/**
	 * 根据ID查找活动
	 */
	@Override
	public ActivityUser findById(Long id) {
		return activityUserRep.findOne(id);
	}
	@Override
	public List<ActivityUser> findByAttributes(Long userId, Long activityId) {
		// TODO Auto-generated method stub
		return activityUserRep.findByAttributes(userId,activityId);
	}

}
