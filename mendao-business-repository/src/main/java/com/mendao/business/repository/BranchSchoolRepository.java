package com.mendao.business.repository;


import org.springframework.stereotype.Repository;

import com.mendao.business.entity.BranchSchool;
import com.mendao.framework.base.jpa.BaseRepository;
@Repository("branchSchoolRepository")
public interface BranchSchoolRepository extends BaseRepository<BranchSchool, Long>  {

}
