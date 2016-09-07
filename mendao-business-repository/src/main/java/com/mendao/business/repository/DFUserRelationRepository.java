package com.mendao.business.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mendao.business.entity.DFUserRelation;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("dFUserRelationRepository")
public interface DFUserRelationRepository extends BaseRepository<DFUserRelation, Long>  {

	@Query("select t.child.id from DFUserRelation t where t.parent.id=:parentId and t.status = 2")
	List<Long> getChildId(@Param("parentId") Long parentId);

	@Query("select t from DFUserRelation t where t.parent.id=:parentId and t.child.id=:childId")
	List<DFUserRelation> getListByProperty(@Param("parentId") Long parentId,@Param("childId") Long childId);

	@Query("select count(d.createUserId.id), d.createUserId.id from DProduct d where d.createUserId.id in (:ids) and d.deleteFlag = 0 group by d.createUserId.id")
	List<Object> getAllDProductByIds(@Param("ids") List<Long> ids);
	
	@Query("select count(f.createUserId.id), f.createUserId.id from FProduct f where f.createUserId.id in (:ids) and f.deleteFlag = 0 group by f.createUserId.id")
	List<Object> getHasFProductByIds(@Param("ids") List<Long> ids);
	
	@Modifying
	@Transactional
	@Query("update DFUserRelation d set d.desc = :message where d.id = :id")
	void updateDFUserRelationDesc(@Param("message") String message, @Param("id") Long id);

	@Query("select t from DFUserRelation t where t.parent.id=:parentId and t.child.id=:childId and t.status = 2")
	List<DFUserRelation> getByProperty(@Param("parentId") Long parentId,@Param("childId") Long childId);

	@Query("select t from DFUserRelation t where t.parent.id=:parentId and t.status = 2")
	List<DFUserRelation> getByParentId(@Param("parentId") Long parentId);

	@Query("select count(f.id) from FProduct f where f.createUserId.id=:createUserId and f.modifyUserId.id=:modifyUserId and f.dProduct.status = 1 and f.dProduct.deleteFlag = 0 and f.onSale = 1 and f.deleteFlag = 0")
	int queryHasFProductById(@Param("modifyUserId") Long modifyUserId,@Param("createUserId") Long createUserId);

	@Modifying
	@Transactional
	@Query("update DFUserRelation d set d.ywDesc = :ywDesc,d.desc = :ywDesc where d.id = :id")
	void updateDFUserRelationYwDesc(@Param("ywDesc") String ywDesc, @Param("id") Long id);
	
	@Query("select t from DFUserRelation t where t.parent.id=:parentId and t.child.id=:childId")
	List<DFUserRelation> getByPartentAndChild(@Param("parentId") Long parentId,@Param("childId") Long childId);
	
	@Query("select count(t) from DFUserRelation t where t.parent.id=:parentId and t.status = 1 and t.type = 1")
	int getApplyCountByUserId(@Param("parentId") Long parentId);

	@Query("select count(t) from DFUserRelation t where t.parent.id=:parentId and t.status = 2")
	int getfriendCount(@Param("parentId") Long parentId);
}
