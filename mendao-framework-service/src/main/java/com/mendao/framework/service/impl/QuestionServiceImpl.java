package com.mendao.framework.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.framework.entity.Question;
import com.mendao.framework.repository.QuestionRepository;
import com.mendao.framework.service.QuestionService;

@Service
public class QuestionServiceImpl  implements QuestionService{

	@Autowired
	QuestionRepository questionRepository;

	/**
	 * 获取全部问题
	 */
	@Override
	public List<Question> getAllQuestion() {
		return questionRepository.getAllQuestion();
	}

	@Override
	public Question findById(Long id) {
		return questionRepository.findOne(id);
	}
	
	
	
}
