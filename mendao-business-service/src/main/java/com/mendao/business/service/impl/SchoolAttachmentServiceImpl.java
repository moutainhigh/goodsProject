package com.mendao.business.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.SchoolAttachment;
import com.mendao.business.repository.SchoolAttachmentRepository;
import com.mendao.business.service.SchoolAttachmentService;
import com.mendao.framework.base.jpa.PageEntity;

@Service("schoolAttachmentService")
public class SchoolAttachmentServiceImpl implements SchoolAttachmentService{

	@Autowired
	private SchoolAttachmentRepository schoolAttachmentRepository;
	/**
	 * 教学环境分页数据
	 */
	@Override
	public PageEntity<SchoolAttachment> getPage(PageEntity<SchoolAttachment> pageEntity) {
		
		return schoolAttachmentRepository.findByPage(pageEntity);
	}
	/**
	 * 保存教学环境
	 */
	@Override
	public void save(SchoolAttachment attachment) {
		schoolAttachmentRepository.save(attachment);
	}
	/**
	 * 根据ID查找教学环境
	 */
	@Override
	public SchoolAttachment findById(Long id) {
		return schoolAttachmentRepository.findOne(id);
	}
	/**
	 * 根据ID删除教学环境
	 */
	@Override
	public void deleteById(Long id) {
		schoolAttachmentRepository.delete(id);
	}

}
