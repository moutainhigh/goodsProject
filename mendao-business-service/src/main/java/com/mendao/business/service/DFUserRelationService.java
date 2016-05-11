package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.DFUserRelation;
import com.mendao.framework.base.jpa.PageEntity;

public interface DFUserRelationService {

	PageEntity<DFUserRelation> getPage(PageEntity<DFUserRelation> pageEntity);

	List<Long> getChildId(Long id);
	
	void addUserToProxy(Long parendId, String ids);

	void deleteById(Long id);

	
}
