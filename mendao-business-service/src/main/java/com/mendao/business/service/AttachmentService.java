package com.mendao.business.service;


import com.mendao.business.entity.Attachment;
import com.mendao.framework.base.jpa.PageEntity;

public interface AttachmentService {

	PageEntity<Attachment> getPage(PageEntity<Attachment> pageEntity);

	void save(Attachment attachment);

	Attachment findById(Long id);

	void deleteById(Long id);
	
	

}
