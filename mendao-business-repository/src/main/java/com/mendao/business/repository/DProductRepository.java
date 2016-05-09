package com.mendao.business.repository;

import org.springframework.stereotype.Repository;

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
	
}
