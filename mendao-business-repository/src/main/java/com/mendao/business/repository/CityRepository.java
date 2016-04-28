package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.City;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("cityRepository")
public interface CityRepository extends BaseRepository<City, Integer>{
	
	@Query("select t from City t, Province p where t.provinceId = p.id and p.name = :name order by t.sortSeq")
	public List<City> findAllByProvinceName(@Param("name") String name);
	
	
	@Query("select t from City t where t.provinceId = :provinceId order by t.sortSeq")
	public List<City> findAllByProvinceId(@Param("provinceId") Integer provinceId);
}
