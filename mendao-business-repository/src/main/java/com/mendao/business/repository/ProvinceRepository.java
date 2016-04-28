package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.Province;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("provinceRepository")
public interface ProvinceRepository extends BaseRepository<Province, Integer>{
	
	@Query("select t from Province t order by t.sortSeq")
	public List<Province> findAllProvince();
}
