package com.mendao.business.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.TopicSticker;
import com.mendao.business.repository.TopicStickerRepository;
import com.mendao.business.service.TopicStickerService;
import com.mendao.framework.base.jpa.PageEntity;

@Service("topicStickerService")
public class TopicStickerServiceImpl implements TopicStickerService{

	@Autowired
	private TopicStickerRepository topicStickerRepository;
	/**
	 * 话题标签分页数据
	 */
	@Override
	public PageEntity<TopicSticker> getPage(PageEntity<TopicSticker> pageEntity) {
		
		return topicStickerRepository.findByPage(pageEntity);
	}
	/**
	 * 保存话题标签
	 */
	@Override
	public void save(TopicSticker sticker) {
		topicStickerRepository.save(sticker);
	}
	/**
	 * 根据ID查找话题标签
	 */
	@Override
	public TopicSticker findById(Long id) {
		return topicStickerRepository.findOne(id);
	}
	/**
	 * 根据ID删除话题标签
	 */
	@Override
	public void deleteById(Long id) {
		topicStickerRepository.delete(id);
	}
	/**
	 * 根据activityID查找话题标签
	 */
	@Override
	public List<TopicSticker> findByTopicId(Long topicId) {
		return topicStickerRepository.findListByHql("from TopicSticker t where t.topic.id = " + topicId);
	}
	/**
	 * 根据activityID删除话题标签
	 */
	@Override
	public void deleteByTopicId(Long topicId) {
		List<TopicSticker> ts = this.findByTopicId(topicId);
		topicStickerRepository.delete(ts);
	}
	@Override
	public List<TopicSticker> findByStickerId(Long stickerId) {
		return topicStickerRepository.findListByHql("from TopicSticker t where t.sticker.id = " + stickerId);
	}

}
