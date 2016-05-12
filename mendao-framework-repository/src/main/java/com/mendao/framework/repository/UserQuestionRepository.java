package com.mendao.framework.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.UserQuestion;
@Repository("userQuestionRepository")
public interface UserQuestionRepository extends BaseRepository<UserQuestion, Long>  {

	@Query("select t from UserQuestion t where t.user.id=:userId ")
	List<UserQuestion> findByUserId(@Param("userId") Long userId);

	
}
