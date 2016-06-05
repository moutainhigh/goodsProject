package com.mendao.framework.service;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.UserRelation;


public interface RecommendService {

	PageEntity<UserRelation> getPage(PageEntity<UserRelation> pageEntity);

	
}
