package com.mendao.framework.repository;
import org.springframework.stereotype.Repository;

import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.UserQuestion;
@Repository("userQuestionRepository")
public interface UserQuestionRepository extends BaseRepository<UserQuestion, Long>  {

	
}
