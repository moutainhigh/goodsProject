package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mendao.business.entity.DProduct;
import com.mendao.framework.base.jpa.BaseRepository;

/**
 * 
 * @ClassName: DProductRepository 
 * @Description: 代理产品dao
 * @author TianMeifeng
 * @date 2016年5月8日 上午1:38:06 
 *
 */
@Repository("pProductRepository")
public interface DProductRepository extends BaseRepository<DProduct, Long>  {
	/**
	 * 批量修改产品状态
	 * @Title: updateProductStatusByIds 
	 * @Description: TODO
	 * @param @param ids    
	 * @return void  
	 * @throws
	 */
	@Modifying
	@Transactional
	@Query("UPDATE DProduct t SET t.status = :status WHERE t.id in (:ids)")
	public void updateProductStatusByIds(@Param("status") Integer status, @Param("ids") List<Long> ids);
	
	/**
	 * 删除代理产品
	 * @Title: deleteDProductById 
	 * @Description: TODO
	 * @param @param id    
	 * @return void  
	 * @throws
	 */
	@Modifying
	@Transactional
	@Query("UPDATE DProduct t SET t.deleteFlag = -1 WHERE t.id = :id")
	public void deleteDProductById(@Param("id") Long id);
}
