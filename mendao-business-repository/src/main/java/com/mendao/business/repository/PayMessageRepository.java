package com.mendao.business.repository;

import org.springframework.stereotype.Repository;

import com.mendao.business.entity.PayMessage;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("payMessageRepository")
public interface PayMessageRepository extends BaseRepository<PayMessage, Long>  {

	
}
