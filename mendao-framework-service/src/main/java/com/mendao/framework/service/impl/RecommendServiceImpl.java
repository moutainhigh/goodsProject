package com.mendao.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.UserRelation;
import com.mendao.framework.repository.UserRelationRepository;
import com.mendao.framework.service.RecommendService;


@Service
public class RecommendServiceImpl implements RecommendService{

	@Autowired
	UserRelationRepository userRelationRepository;
	
	@Override
	public PageEntity<UserRelation> getPage(PageEntity<UserRelation> pageEntity) {
		return userRelationRepository.findByPage(pageEntity);
	}

	
}
