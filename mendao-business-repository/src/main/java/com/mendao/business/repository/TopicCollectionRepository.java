package com.mendao.business.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.TopicCollection;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("topicCollectionRepository")
public interface TopicCollectionRepository extends BaseRepository<TopicCollection, Long>  {

	@Query("select t from TopicCollection t where t.author.id = :aothorId and t.topic.id = :topicId order by t.id asc")
	List<TopicCollection> findByAttributes(@Param("aothorId") Long aothorId, @Param("topicId") Long topicId);

   

}
