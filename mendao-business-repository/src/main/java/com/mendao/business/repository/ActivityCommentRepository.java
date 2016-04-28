package com.mendao.business.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.ActivityComment;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("activityCommentRepository")
public interface ActivityCommentRepository extends BaseRepository<ActivityComment, Long>  {

	@Query("select t from ActivityComment t where t.parent.id = :id and t.referLever = 2 order by t.id desc")
	List<ActivityComment> getCommentByParentId(@Param("id")  Long id);

}
