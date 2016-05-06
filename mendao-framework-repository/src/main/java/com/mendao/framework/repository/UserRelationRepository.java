package com.mendao.framework.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.UserRelation;
@Repository("userRelationRepository")
public interface UserRelationRepository extends BaseRepository<UserRelation, Long>  {

	@Query("select t from UserRelation t where t.parent.id=:parentId ")
	List<UserRelation> getUserRelationByParentId(@Param("parentId") Long parentId);
	
	@Query("select t from UserRelation t where t.currentUser.id=:currentUserId ")
	UserRelation getUserRelationByCurrentUserId(@Param("currentUserId") Long currentUserId);
	
}
