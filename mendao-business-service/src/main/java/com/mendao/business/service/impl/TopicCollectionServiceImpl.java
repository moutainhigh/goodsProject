package com.mendao.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.TopicCollection;
import com.mendao.business.repository.TopicCollectionRepository;
import com.mendao.business.service.TopicCollectionService;
import com.mendao.framework.base.jpa.PageEntity;
/**
 * 话题收藏service实现类
 * @author warden
 */
@Service("topicCollectionService")
public class TopicCollectionServiceImpl implements TopicCollectionService{

	@Autowired
	private TopicCollectionRepository topicCollectionRep;
	/**
	 * 获取收藏话题列表分页
	 */
	@Override
	public PageEntity<TopicCollection> getPage(PageEntity<TopicCollection> myPage) {
		return topicCollectionRep.findByPage(myPage);
	}
	/**
	 * 收藏话题
	 */
	@Override
	public void save(TopicCollection topicCollection) {
		topicCollectionRep.merge(topicCollection);
	}
	/**
	 * 根据ID查找收藏的话题
	 */
	@Override
	public TopicCollection findById(Long id) {
		return topicCollectionRep.findOne(id);
	}
	
	@Override
	public List<TopicCollection> findByAttributes(Long aothorId, Long topicId) {
		return topicCollectionRep.findByAttributes(aothorId,topicId);
	}

}
