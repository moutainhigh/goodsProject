package com.mendao.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.SchoolComment;
import com.mendao.business.repository.SchoolCommentRepository;
import com.mendao.business.service.SchoolCommentService;
import com.mendao.framework.base.jpa.PageEntity;
/**
 * 机构评论实现类
 * @author warden
 */
@Service("schoolCommentService")
public class SchoolCommentServiceImpl implements SchoolCommentService{

	@Autowired
	private SchoolCommentRepository schoolCommentRep;
	/**
	 * 获取机构评论列表分页
	 */
	@Override
	public PageEntity<SchoolComment> getPage(PageEntity<SchoolComment> myPage) {
		return schoolCommentRep.findByPage(myPage);
	}
	/**
	 * 保存机构评论
	 */
	@Override
	public void save(SchoolComment schoolComment) {
		schoolCommentRep.save(schoolComment);
	}
	/**
	 * 根据ID查找评论
	 */
	@Override
	public SchoolComment findById(Long id) {
		return schoolCommentRep.findOne(id);
	}
	/**
	 * 根据父级ID获取所有子集的评论
	 */
	@Override
	public List<SchoolComment> getCommentByParentId(Long id) {
		return schoolCommentRep.getCommentByParentId(id);
	}

}
