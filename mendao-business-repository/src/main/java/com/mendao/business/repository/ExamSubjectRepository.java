package com.mendao.business.repository;

import org.springframework.stereotype.Repository;

import com.mendao.business.entity.ExamSubject;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("examSubjectRepository")
public interface ExamSubjectRepository extends BaseRepository<ExamSubject, Long>{
}
