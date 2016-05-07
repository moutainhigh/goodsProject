package com.mendao.framework.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.Role;
@Repository("roleRepository")
public interface RoleRepository extends BaseRepository<Role, Long>  {

	@Query("select t from Role t where t.uuid=:uuid ")
	public Role findByUuid(@Param("uuid") String uuid);

	@Query("select t from Role t order by id asc ")
	public List<Role> getAllRole();
	
}
