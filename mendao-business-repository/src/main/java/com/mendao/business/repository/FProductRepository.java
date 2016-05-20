package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mendao.business.entity.FProduct;
import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.ShopUser;

@Repository("fProductRepository")
public interface FProductRepository extends BaseRepository<FProduct, Long>  {

	@Query("select f.createUserId from FProduct f where f.modifyUserId.id = :id group by f.createUserId")
	public List<ShopUser> queryDailiByFenxiao(@Param("id") Long id);
	
	@Modifying
	@Transactional
	@Query("UPDATE FProduct f SET f.onSale = :onSale WHERE f.id in (:ids)")
	public void updateFProductOnSaleByIds(@Param("onSale") Integer onSale, @Param("ids") List<Long> ids);
	
	@Modifying
	@Transactional
	@Query("UPDATE FProduct t SET t.deleteFlag = -1 WHERE t.id = :id")
	public void deleteFProductById(@Param("id") Long id);
}
