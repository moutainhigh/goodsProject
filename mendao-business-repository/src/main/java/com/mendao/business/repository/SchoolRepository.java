package com.mendao.business.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.School;
import com.mendao.framework.base.jpa.BaseRepository;
@Repository("schoolRepository")
public interface SchoolRepository extends BaseRepository<School, Long>  {

}
