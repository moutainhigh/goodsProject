package com.mendao.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.TopicComment;
import com.mendao.business.repository.TopicCommentRepository;
import com.mendao.business.service.TopicCommentService;
import com.mendao.framework.base.jpa.PageEntity;
/**
 * 话题评论实现类
 * @author warden
 */
@Service("topicCommentService")
public class TopicCommentServiceImpl implements TopicCommentService{

	@Autowired
	private TopicCommentRepository topicCommentRep;
	/**
	 * 获取话题评论列表分页
	 */
	@Override
	public PageEntity<TopicComment> getPage(PageEntity<TopicComment> myPage) {
		return topicCommentRep.findByPage(myPage);
	}
	/**
	 * 保存话题评论
	 */
	@Override
	public void save(TopicComment topicComment) {
		topicCommentRep.save(topicComment);
	}
	/**
	 * 根据ID查找评论
	 */
	@Override
	public TopicComment findById(Long id) {
		return topicCommentRep.findOne(id);
	}
	/**
	 * 根据父级ID获取所有子集的评论
	 */
	@Override
	public List<TopicComment> getCommentByParentId(Long id) {
		return topicCommentRep.getCommentByParentId(id);
	}

}
