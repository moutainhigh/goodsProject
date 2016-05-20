package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mendao.business.entity.ProductPic;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("productPicRepository")
public interface ProductPicRepository extends BaseRepository<ProductPic, Long>  {

	@Query("select t from ProductPic t where t.dproduct.id=:id ")
	List<ProductPic> getPicByDProductId(@Param("id")  Long id);
	
	@Modifying
	@Transactional
	@Query("delete from ProductPic t WHERE t.dproduct.id=:id")
	void deletePicByDProductId(@Param("id") Long id);

	@Modifying
	@Query("delete from ProductPic t WHERE t.fproduct.id=:id")
	void deleteByFproductId(@Param("id") Long id);

	
}
