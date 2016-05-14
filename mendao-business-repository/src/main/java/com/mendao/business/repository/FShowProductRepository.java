package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.FShowProduct;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("fShowProductRepository")
public interface FShowProductRepository extends BaseRepository<FShowProduct, Long>  {

	@Query("select t.dproduct.id from FShowProduct t where t.user.id=:userId ")
	List<Long> getDProductByUserId(@Param("userId") Long userId);

	
}
