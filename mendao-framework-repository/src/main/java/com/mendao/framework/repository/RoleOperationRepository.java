package com.mendao.framework.repository;
import org.springframework.stereotype.Repository;

import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.RoleOperation;
@Repository("roleOperationRepository")
public interface RoleOperationRepository extends BaseRepository<RoleOperation, Long>  {
	
	
	
}
