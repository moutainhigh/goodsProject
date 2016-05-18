package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.DFUserRelation;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("dFUserRelationRepository")
public interface DFUserRelationRepository extends BaseRepository<DFUserRelation, Long>  {

	@Query("select t.child.id from DFUserRelation t where t.parent.id=:parentId and t.status = 2")
	List<Long> getChildId(@Param("parentId") Long parentId);

	@Query("select t.child.id from DFUserRelation t where t.parent.id=:parentId and t.child.id=:childId and t.status = 2")
	List<DFUserRelation> getListByProperty(@Param("parentId") Long parentId,@Param("childId") Long childId);

	
}
