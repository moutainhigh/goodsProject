package com.mendao.business.service;


import com.mendao.business.entity.SchoolAttachment;
import com.mendao.framework.base.jpa.PageEntity;

public interface SchoolAttachmentService {

	PageEntity<SchoolAttachment> getPage(PageEntity<SchoolAttachment> pageEntity);

	void save(SchoolAttachment attachment);

	SchoolAttachment findById(Long id);

	void deleteById(Long id);
	
	

}
