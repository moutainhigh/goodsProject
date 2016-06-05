package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.PKind;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("pKindRepository")
public interface PKindRepository extends BaseRepository<PKind, Long>  {

	@Query("select t from PKind t where t.kindName=:kindName ")
	List<PKind> getListByName(@Param("kindName") String kindName);
	
	
	
}
