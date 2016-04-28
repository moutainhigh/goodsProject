package com.mendao.business.repository;

import org.springframework.stereotype.Repository;

import com.mendao.business.entity.Exam;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("examRepository")
public interface ExamRepository extends BaseRepository<Exam, Long>{
	
}
