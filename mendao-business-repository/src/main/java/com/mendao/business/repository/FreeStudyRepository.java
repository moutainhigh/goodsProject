package com.mendao.business.repository;


import org.springframework.stereotype.Repository;

import com.mendao.business.entity.FreeStudy;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("freeStudyRepository")
public interface FreeStudyRepository extends BaseRepository<FreeStudy, Long>  {


}
