package com.mendao.business.repository;


import org.springframework.stereotype.Repository;

import com.mendao.business.entity.Activity;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("activityRepository")
public interface ActivityRepository extends BaseRepository<Activity, Long>  {

   

}
