package com.mendao.business.repository;


import org.springframework.stereotype.Repository;

import com.mendao.business.entity.Feedback;
import com.mendao.framework.base.jpa.BaseRepository;
@Repository("feedbackRepository")
public interface FeedbackRepository extends BaseRepository<Feedback, Long>  {
	
}
