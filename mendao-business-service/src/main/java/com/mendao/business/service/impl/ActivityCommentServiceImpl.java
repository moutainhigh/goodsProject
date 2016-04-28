package com.mendao.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.ActivityComment;
import com.mendao.business.repository.ActivityCommentRepository;
import com.mendao.business.service.ActivityCommentService;
import com.mendao.framework.base.jpa.PageEntity;
/**
 * 活动评论实现类
 * @author warden
 */
@Service("activityCommentService")
public class ActivityCommentServiceImpl implements ActivityCommentService{

	@Autowired
	private ActivityCommentRepository activityCommentRep;
	/**
	 * 获取话题评论列表分页
	 */
	@Override
	public PageEntity<ActivityComment> getPage(PageEntity<ActivityComment> myPage) {
		return activityCommentRep.findByPage(myPage);
	}
	/**
	 * 保存话题评论
	 */
	@Override
	public void save(ActivityComment activityComment) {
		activityCommentRep.save(activityComment);
	}
	/**
	 * 根据ID查找评论
	 */
	@Override
	public ActivityComment findById(Long id) {
		return activityCommentRep.findOne(id);
	}
	/**
	 * 根据父级ID获取所有子集的评论
	 */
	@Override
	public List<ActivityComment> getCommentByParentId(Long id) {
		return activityCommentRep.getCommentByParentId(id);
	}

}
