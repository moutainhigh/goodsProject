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
	@Query("UPDATE FProduct f SET f.deleteFlag = :deleteFlag WHERE f.id in (:ids)")
	public void updateFProductDeleteFlagByIds(@Param("deleteFlag") Integer deleteFlag, @Param("ids") List<Long> ids);
	
	@Modifying
	@Transactional
	@Query("DELETE FProduct t WHERE t.id = :id")
	public void deleteFProductById(@Param("id") Long id);
	
	@Modifying
	@Transactional
	@Query("UPDATE FProduct t SET t.deleteFlag = -1 WHERE t.dProduct.id = :id")
	public void deleteFProductByDProductId(@Param("id") Long id);
	
	@Query("select t from FProduct t where t.createUserId.id=:createUserId and t.modifyUserId.id=:modifyUserId and t.dProduct.id=:dProductId ")
	FProduct getByProperty(@Param("modifyUserId")Long modifyUserId,@Param("createUserId") Long createUserId,@Param("dProductId") Long dProductId);

	@Query("select t from FProduct t where t.modifyUserId.id=:modifyUserId and t.id != :id order by t.id desc ")
	public List<FProduct> getByModifyUserId(@Param("modifyUserId") Long modifyUserId,@Param("id") Long id);
	
	@Query("select t from FProduct t where t.modifyUserId.id=:modifyUserId and t.createUserId.id = :createUserId order by t.id desc ")
	public List<FProduct> getByModifyUserIdAndCreateUserId(@Param("modifyUserId") Long modifyUserId,@Param("createUserId") Long id);

	@Modifying
	@Transactional
	@Query("DELETE FProduct t where t.createUserId.id=:createUserId and t.modifyUserId.id=:modifyUserId and t.dProduct.id not in (:dproductIds)")
	public void deleteByUsersId(@Param("createUserId") Long createUserId, @Param("modifyUserId") Long modifyUserId, @Param("dproductIds") String dproductIds);

	@Query("select count(f.id) from FProduct f where f.modifyUserId.id = :id and f.deleteFlag = 0 and f.onSale = 1")
	public int getOnSoleProductNum(@Param("id") Long id);
}
