package com.mendao.framework.repository;


import org.springframework.stereotype.Repository;

import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.FwAccountIO;
@Repository("fwAccountIORepository")
public interface AccountIORepository extends BaseRepository<FwAccountIO, Long>  {


}
