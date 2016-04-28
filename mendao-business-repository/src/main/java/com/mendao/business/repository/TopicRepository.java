package com.mendao.business.repository;


import org.springframework.stereotype.Repository;

import com.mendao.business.entity.Topic;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("topicRepository")
public interface TopicRepository extends BaseRepository<Topic, Long>  {

   

}
