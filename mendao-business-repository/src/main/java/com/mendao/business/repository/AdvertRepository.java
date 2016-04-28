package com.mendao.business.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.Advert;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("advertRepository")
public interface AdvertRepository extends BaseRepository<Advert, Long>  {
	
	@Query("select t from Advert t where t.place.dicValue =:place order by t.id desc")
	List<Advert> getListByPlace(@Param("place") String place);

}
