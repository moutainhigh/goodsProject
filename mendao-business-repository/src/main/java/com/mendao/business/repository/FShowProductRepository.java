package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mendao.business.entity.FShowProduct;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("fShowProductRepository")
public interface FShowProductRepository extends BaseRepository<FShowProduct, Long>  {

	@Query("select t.dproduct.id from FShowProduct t where t.user.id=:userId ")
	List<Long> getDProductByUserId(@Param("userId") Long userId);
	@Modifying
	@Transactional
	@Query("delete from FShowProduct f where f.dproduct.id = :id")
	void deleteByDProductId(@Param("id") Long id);
	
	@Modifying
	@Transactional
	@Query("delete from FShowProduct f where f.user.id = :id and f.dproduct.id not in (:dproductIds)")
	void deleteByUserId(@Param("id") Long id,@Param("dproductIds") String dproductIds);
	
	@Query("select t from FShowProduct t where t.user.id=:userId and t.dproduct.id=:dproductId ")
	List<FShowProduct> getByProperty(@Param("userId") Long userId,@Param("dproductId") Long dproductId);
}
