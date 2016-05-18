package com.mendao.business.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.ShopMessage;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("shopMessageRepository")
public interface ShopMessageRepository extends BaseRepository<ShopMessage, Long>  {

	@Query("select t from ShopMessage t where t.user.id=:userId ")
	ShopMessage findByUserId(@Param("userId") Long userId);

	
	
}
