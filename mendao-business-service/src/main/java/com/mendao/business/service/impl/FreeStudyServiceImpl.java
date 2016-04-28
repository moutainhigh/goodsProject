package com.mendao.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.FreeStudy;
import com.mendao.business.repository.FreeStudyRepository;
import com.mendao.business.service.FreeStudyService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.util.Util;

/**
 * 免费学报名service实现类
 * @author warden
 */
@Service("freeStudyService")
public class FreeStudyServiceImpl implements FreeStudyService{

	@Autowired
	private FreeStudyRepository freeStudyRep;
	/**
	 * 获取活动列表分页
	 */
	@Override
	public PageEntity<FreeStudy> getPage(PageEntity<FreeStudy> myPage) {
		return freeStudyRep.findByPage(myPage);
	}
	/**
	 * 保存免费学报名
	 */
	@Override
	public void save(FreeStudy freeStudy) {
		freeStudyRep.save(freeStudy);
	}
	/**
	 * 根据ID查找免费学报名
	 */
	@Override
	public FreeStudy findById(Long id) {
		return freeStudyRep.findOne(id);
	}
	/**
	 * 根据ids查找免费学报名
	 */
	@Override
	public List<FreeStudy> findListByIds(String[] ids) {
		String param =Util.getIdSQLParam(ids);
		if (param != null) {
			return freeStudyRep.findListByHql("from Activity t where t.id in (" + param + ")");
		}
		return null;
	}

}
