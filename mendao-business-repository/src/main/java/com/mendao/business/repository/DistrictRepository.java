package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.District;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("districtRepository")
public interface DistrictRepository extends BaseRepository<District, Integer>{
	@Query("select t from District t, City p where t.cityId = p.id and p.name = :city order by t.sortSeq")
	public List<District> findAllByCityName(@Param("city") String city);
	
	@Query("select t from District t where t.cityId = :cityId order by t.sortSeq")
	public List<District> findAllByCityId(@Param("cityId") Integer cityId);
}
