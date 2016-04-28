package com.mendao.business.service;


import java.util.List;

import com.mendao.business.entity.ActivitySticker;
import com.mendao.framework.base.jpa.PageEntity;

public interface ActivityStickerService {

	PageEntity<ActivitySticker> getPage(PageEntity<ActivitySticker> pageEntity);

	void save(ActivitySticker sticker);

	ActivitySticker findById(Long id);

	void deleteById(Long id);

	List<ActivitySticker> findByActivityId(Long activityId);
	
	void deleteByActivityId(Long activityId);

	List<ActivitySticker> findByStickerId(Long valueOf);
	
}
