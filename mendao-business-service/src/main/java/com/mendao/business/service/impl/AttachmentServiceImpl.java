package com.mendao.business.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.Attachment;
import com.mendao.business.repository.AttachmentRepository;
import com.mendao.business.service.AttachmentService;
import com.mendao.framework.base.jpa.PageEntity;

@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService{

	@Autowired
	private AttachmentRepository attachmentRepository;
	/**
	 * 标签分页数据
	 */
	@Override
	public PageEntity<Attachment> getPage(PageEntity<Attachment> pageEntity) {
		
		return attachmentRepository.findByPage(pageEntity);
	}
	/**
	 * 保存标签
	 */
	@Override
	public void save(Attachment attachment) {
		attachmentRepository.save(attachment);
	}
	/**
	 * 根据ID查找标签
	 */
	@Override
	public Attachment findById(Long id) {
		return attachmentRepository.findOne(id);
	}
	/**
	 * 根据ID删除标签
	 */
	@Override
	public void deleteById(Long id) {
		attachmentRepository.delete(id);
	}

}
