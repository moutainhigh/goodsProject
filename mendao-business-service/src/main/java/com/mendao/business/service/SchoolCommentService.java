package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.SchoolComment;
import com.mendao.framework.base.jpa.PageEntity;

public interface SchoolCommentService {
	public PageEntity<SchoolComment> getPage(PageEntity<SchoolComment> myPage);

	public void save(SchoolComment schoolComment);

	public SchoolComment findById(Long id);

	public List<SchoolComment> getCommentByParentId(Long id);
}
