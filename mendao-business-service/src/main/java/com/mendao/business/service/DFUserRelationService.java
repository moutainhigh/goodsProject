package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.DFUserRelation;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.ShopUser;

public interface DFUserRelationService {

	PageEntity<DFUserRelation> getPage(PageEntity<DFUserRelation> pageEntity);

	List<Long> getChildId(Long id);
	
	void addUserToProxy(Long parendId, ShopUser shopUser);

	void deleteById(Long id);

	List<DFUserRelation> getListByProperty(Long parentId, Long childId);

	
}
