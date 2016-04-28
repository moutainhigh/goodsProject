package com.mendao.business.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.ActivityUser;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("activityUserRepository")
public interface ActivityUserRepository extends BaseRepository<ActivityUser, Long>  {

	@Query("select t from ActivityUser t where t.user.id = :userId and t.activity.id = :activityId order by t.id asc")
	List<ActivityUser> findByAttributes(@Param("userId") Long userId, @Param("activityId") Long activityId);

}
