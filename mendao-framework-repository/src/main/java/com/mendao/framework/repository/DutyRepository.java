package com.mendao.framework.repository;
import org.springframework.stereotype.Repository;

import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.FwDuty;
@Repository("dutyRepository")
public interface DutyRepository extends BaseRepository<FwDuty, Long>  {
}
