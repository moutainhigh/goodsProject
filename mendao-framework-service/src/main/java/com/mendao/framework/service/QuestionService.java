package com.mendao.framework.service;

import java.util.List;

import com.mendao.framework.entity.Question;



public interface QuestionService  {
	/**
	 * 获取全部的保密问题
	 * @return
	 */
	List<Question> getAllQuestion();

	Question findById(Long id);
	
	
}
