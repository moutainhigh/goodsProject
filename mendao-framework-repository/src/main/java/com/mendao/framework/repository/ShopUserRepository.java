package com.mendao.framework.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.ShopUser;
@Repository("shopUserRepository")
public interface ShopUserRepository extends BaseRepository<ShopUser, Long>  {

	@Query("select t from ShopUser t where t.userName=:userName and t.status = 1")
	List<ShopUser> getUserByUserName(@Param("userName") String userName);

	@Query("select t from ShopUser t where t.phone=:phone and t.status = 1 ")
	List<ShopUser> getUserByPhone(@Param("phone") String phone);

	@Query("select t from ShopUser t where t.userName=:userName and t.password=:password and t.status = 1")
	ShopUser login(@Param("userName") String userName,@Param("password") String password);
	
	@Query("select t from ShopUser t where t.uuid=:uuid and t.status = 1")
	ShopUser getUserByUuid(@Param("uuid") String uuid);

	@Query("select t from ShopUser t where t.email=:email and t.status = 1 ")
	List<ShopUser> getUserByEmail(@Param("email") String email);
	
	
	
}
