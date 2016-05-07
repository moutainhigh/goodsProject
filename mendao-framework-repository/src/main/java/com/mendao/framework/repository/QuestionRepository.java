package com.mendao.framework.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.Question;
@Repository("questionRepository")
public interface QuestionRepository extends BaseRepository<Question, Long>  {

	@Query("select t from Question t order by t.sort desc ")
	List<Question> getAllQuestion();

	
}
