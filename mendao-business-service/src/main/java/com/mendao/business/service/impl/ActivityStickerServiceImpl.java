package com.mendao.business.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.ActivitySticker;
import com.mendao.business.repository.ActivityStickerRepository;
import com.mendao.business.service.ActivityStickerService;
import com.mendao.framework.base.jpa.PageEntity;

@Service("activityStickerService")
public class ActivityStickerServiceImpl implements ActivityStickerService{

	@Autowired
	private ActivityStickerRepository activityStickerRepository;
	/**
	 * 活动标签分页数据
	 */
	@Override
	public PageEntity<ActivitySticker> getPage(PageEntity<ActivitySticker> pageEntity) {
		
		return activityStickerRepository.findByPage(pageEntity);
	}
	/**
	 * 保存活动标签
	 */
	@Override
	public void save(ActivitySticker sticker) {
		activityStickerRepository.save(sticker);
	}
	/**
	 * 根据ID查找活动标签
	 */
	@Override
	public ActivitySticker findById(Long id) {
		return activityStickerRepository.findOne(id);
	}
	/**
	 * 根据ID删除活动标签
	 */
	@Override
	public void deleteById(Long id) {
		activityStickerRepository.delete(id);
	}
	/**
	 * 根据activityID查找活动标签
	 */
	@Override
	public List<ActivitySticker> findByActivityId(Long activityId) {
		return activityStickerRepository.findListByHql("from ActivitySticker t where t.activity.id = " + activityId);
	}
	/**
	 * 根据activityID删除活动标签
	 */
	@Override
	public void deleteByActivityId(Long activityId) {
		List<ActivitySticker> as = this.findByActivityId(activityId);
		activityStickerRepository.delete(as);
	}
	/**
	 * 根据stickerId查找活动标签
	 */
	@Override
	public List<ActivitySticker> findByStickerId(Long stickerId) {
		return activityStickerRepository.findListByHql("from ActivitySticker t where t.sticker.id = " + stickerId);
	}

}
