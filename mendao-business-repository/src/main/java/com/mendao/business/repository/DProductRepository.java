package com.mendao.business.repository;

import org.springframework.stereotype.Repository;

import com.mendao.business.entity.DProduct;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("pProductRepository")
public interface DProductRepository extends BaseRepository<DProduct, Long>  {

	
}
