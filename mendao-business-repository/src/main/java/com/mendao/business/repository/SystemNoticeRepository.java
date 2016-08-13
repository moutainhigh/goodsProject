package com.mendao.business.repository;

import org.springframework.stereotype.Repository;

import com.mendao.business.entity.SystemNotice;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("systemNoticeRepository")
public interface SystemNoticeRepository extends BaseRepository<SystemNotice, Long>  {

	
}
