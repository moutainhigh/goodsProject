package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.RegisterLink;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("registerLinkRepository")
public interface RegisterLinkRepository extends BaseRepository<RegisterLink, Long>  {

	@Query("select t from RegisterLink t where t.user.id=:userId ")
	List<RegisterLink> getLinkByUserId(@Param("userId") Long userId);

	
}
