package com.mendao.business.repository;

import org.springframework.stereotype.Repository;

import com.mendao.business.entity.FShowProduct;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("fShowProductRepository")
public interface FShowProductRepository extends BaseRepository<FShowProduct, Long>  {

	
}
