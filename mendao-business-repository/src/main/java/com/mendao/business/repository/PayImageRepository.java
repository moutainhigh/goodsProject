package com.mendao.business.repository;

import org.springframework.stereotype.Repository;

import com.mendao.business.entity.PayImage;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("payImageRepository")
public interface PayImageRepository extends BaseRepository<PayImage, Long>  {

	
}
