package com.mendao.business.service;


import java.util.List;

import com.mendao.business.entity.SchoolSticker;
import com.mendao.framework.base.jpa.PageEntity;

public interface SchoolStickerService {

	PageEntity<SchoolSticker> getPage(PageEntity<SchoolSticker> pageEntity);

	void save(SchoolSticker sticker);

	SchoolSticker findById(Long id);

	void deleteById(Long id);

	List<SchoolSticker> findBySchoolId(Long schoolId);
	
}
