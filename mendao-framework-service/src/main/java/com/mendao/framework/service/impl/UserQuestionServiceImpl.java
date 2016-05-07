package com.mendao.framework.service.impl;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.framework.entity.UserQuestion;
import com.mendao.framework.repository.UserQuestionRepository;
import com.mendao.framework.service.UserQuestionService;

@Service
public class UserQuestionServiceImpl  implements UserQuestionService{

	@Autowired
	UserQuestionRepository userQuestionRepository;

	/**
	 * 增加用户保密问题
	 */
	@Override
	public void addUserQuestion(UserQuestion uq) {
		userQuestionRepository.save(uq);
	}
	
}
