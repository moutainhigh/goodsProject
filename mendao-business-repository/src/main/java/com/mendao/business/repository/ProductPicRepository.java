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

	@Query("select t from ProductPic t where t.dproduct.id=:id order by t.id asc")
	List<ProductPic> getPicByDProductId(@Param("id")  Long id);
	
	@Modifying
	@Transactional
	@Query("delete from ProductPic t WHERE t.dproduct.id=:id")
	void deletePicByDProductId(@Param("id") Long id);

	@Modifying
	@Transactional
	@Query("delete from ProductPic t WHERE t.fproduct.id=:id")
	void deleteByFproductId(@Param("id") Long id);
	
	/**
	 * 根据代理产品的id 删除所有分销该产品的图片
	 * @Title: deletePicByDProductId1 
	 * @Description: TODO
	 * @param @param id    
	 * @return void  
	 * @throws
	 */
	@Modifying
	@Transactional
	@Query("delete from ProductPic p where p.fproduct.id in (select f.id from FProduct f where f.dProduct.id = :id)")
	void deletePicByDProductId1(@Param("id") Long id);

	@Query("select t from ProductPic t where t.fproduct.id=:id order by t.id asc")
	List<ProductPic> getPicByFProductId(@Param("id") Long id);

	
}
