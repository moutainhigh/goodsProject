package com.mendao.business.repository;


import org.springframework.stereotype.Repository;

import com.mendao.business.entity.TopicSticker;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("topicStickerRepository")
public interface TopicStickerRepository extends BaseRepository<TopicSticker, Long>  {

   

}
