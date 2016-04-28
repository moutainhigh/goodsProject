package com.mendao.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.Recommend;
import com.mendao.business.entity.School;
import com.mendao.business.repository.RecommendRepository;
import com.mendao.business.service.RecommendService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.util.Util;

@Service("recommendService")
public class RecommendServiceImpl implements RecommendService{

	@Autowired
	RecommendRepository recommendRep;
	
	@Override
	public PageEntity<Recommend> getPage(PageEntity<Recommend> myPage) {
		return recommendRep.findByPage(myPage);
	}

	@Override
	public void save(Recommend recommend) {
		recommendRep.save(recommend);
	}

	@Override
	public Recommend findById(Long id) {
		return recommendRep.findOne(id);
	}

	@Override
	public List<Recommend> findListByIds(String[] ids) {
		String param =Util.getIdSQLParam(ids);
		if (param != null) {
			return recommendRep.findListByHql("from Recommend t where t.id in (" + param + ")");
		}
		return null;
	}

	@Override
	public void deleteAll(List<Recommend> recommends) {
		recommendRep.delete(recommends);
	}

	@Override
	public List<Recommend> getIdListByType(int type) {
		return recommendRep.getIdListByType(type);
	}

	@Override
	public List<School> getSchoolListByAttributes(int type, Long categoryId) {
		return recommendRep.getSchoolListByAttributes(type,categoryId);
	}

}
