package com.mendao.business.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.TopicComment;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("topicCommentRepository")
public interface TopicCommentRepository extends BaseRepository<TopicComment, Long>  {

	@Query("select t from TopicComment t where t.parent.id = :id and t.referLever = 2 order by t.id desc")
	List<TopicComment> getCommentByParentId(@Param("id")  Long id);

}
