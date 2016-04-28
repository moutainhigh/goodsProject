package com.mendao.business.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.SchoolSticker;
import com.mendao.business.repository.SchoolStickerRepository;
import com.mendao.business.service.SchoolStickerService;
import com.mendao.framework.base.jpa.PageEntity;

@Service("schoolStickerService")
public class SchoolStickerServiceImpl implements SchoolStickerService{

	@Autowired
	private SchoolStickerRepository schoolStickerRepository;
	/**
	 * 机构标签分页数据
	 */
	@Override
	public PageEntity<SchoolSticker> getPage(PageEntity<SchoolSticker> pageEntity) {
		
		return schoolStickerRepository.findByPage(pageEntity);
	}
	/**
	 * 保存机构标签
	 */
	@Override
	public void save(SchoolSticker sticker) {
		schoolStickerRepository.save(sticker);
	}
	/**
	 * 根据ID查找机构标签
	 */
	@Override
	public SchoolSticker findById(Long id) {
		return schoolStickerRepository.findOne(id);
	}
	/**
	 * 根据ID删除机构标签
	 */
	@Override
	public void deleteById(Long id) {
		schoolStickerRepository.delete(id);
	}
	/**
	 * 根据schoolID查找机构标签
	 */
	@Override
	public List<SchoolSticker> findBySchoolId(Long schoolId) {
		return schoolStickerRepository.findListByHql("from SchoolSticker t where t.school.id = " + schoolId);
	}

}
