package com.mendao.business.repository;

import org.springframework.stereotype.Repository;

import com.mendao.business.entity.FProduct;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("fProductRepository")
public interface FProductRepository extends BaseRepository<FProduct, Long>  {

	
}
