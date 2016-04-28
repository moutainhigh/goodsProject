package com.mendao.framework.repository;
import org.springframework.stereotype.Repository;

import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.FwDutySource;
@Repository("dutySourceRepository")
public interface DutyActionRepository extends BaseRepository<FwDutySource, Long>  {
}
