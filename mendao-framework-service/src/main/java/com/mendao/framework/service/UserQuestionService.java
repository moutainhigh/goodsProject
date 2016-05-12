package com.mendao.framework.service;

import java.util.List;

import com.mendao.framework.entity.UserQuestion;


public interface UserQuestionService  {

	/**
	 * 增加用户保密问题
	 * @param uq
	 */
	void addUserQuestion(UserQuestion uq);

	/**
	 * 根据用户ID获取用户保密问题
	 * @param id
	 * @return
	 */
	List<UserQuestion> findByUserId(Long id);
	
}
