package com.mendao.business.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.SchoolComment;
import com.mendao.framework.base.jpa.BaseRepository;
@Repository("schoolCommentRepository")
public interface SchoolCommentRepository extends BaseRepository<SchoolComment, Long>  {

	@Query("select t from SchoolComment t where t.parent.id = :id and t.referLever = 2 order by t.id desc")
	List<SchoolComment> getCommentByParentId(@Param("id")  Long id);
}
