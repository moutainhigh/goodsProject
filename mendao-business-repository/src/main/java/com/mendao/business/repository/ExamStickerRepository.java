package com.mendao.business.repository;

import org.springframework.stereotype.Repository;

import com.mendao.business.entity.ExamSticker;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("examStickerRepository")
public interface ExamStickerRepository extends BaseRepository<ExamSticker, Long>{
	
}
