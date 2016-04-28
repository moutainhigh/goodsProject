package com.mendao.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.Feedback;
import com.mendao.business.repository.FeedbackRepository;
import com.mendao.business.service.FeedbackService;

@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	FeedbackRepository feedbackRep;
	@Override
	public boolean save(Feedback feedback) {
		feedbackRep.save(feedback);
		return true;
	}
}
