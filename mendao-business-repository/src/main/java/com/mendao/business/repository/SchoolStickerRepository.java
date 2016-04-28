package com.mendao.business.repository;


import org.springframework.stereotype.Repository;

import com.mendao.business.entity.SchoolSticker;
import com.mendao.framework.base.jpa.BaseRepository;
@Repository("schoolStickerRepository")
public interface SchoolStickerRepository extends BaseRepository<SchoolSticker, Long>  {

}
