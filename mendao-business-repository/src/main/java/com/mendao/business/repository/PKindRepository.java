package com.mendao.business.repository;

import org.springframework.stereotype.Repository;

import com.mendao.business.entity.PKind;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("pKindRepository")
public interface PKindRepository extends BaseRepository<PKind, Long>  {
	
	
	
}
