package com.mendao.business.service;


import java.util.List;

import com.mendao.business.entity.TopicSticker;
import com.mendao.framework.base.jpa.PageEntity;

public interface TopicStickerService {

	PageEntity<TopicSticker> getPage(PageEntity<TopicSticker> pageEntity);

	void save(TopicSticker sticker);

	TopicSticker findById(Long id);

	void deleteById(Long id);

	List<TopicSticker> findByTopicId(Long topicId);
	
	void deleteByTopicId(Long topicId);

	List<TopicSticker> findByStickerId(Long stickerId);
	
}
