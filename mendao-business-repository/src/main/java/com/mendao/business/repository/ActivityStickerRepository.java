package com.mendao.business.repository;


import org.springframework.stereotype.Repository;

import com.mendao.business.entity.ActivitySticker;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("activityStickerRepository")
public interface ActivityStickerRepository extends BaseRepository<ActivitySticker, Long>  {

   

}
