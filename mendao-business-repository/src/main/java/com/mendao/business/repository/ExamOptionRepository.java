package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.ExamOption;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("examOptionRepository")
public interface ExamOptionRepository extends BaseRepository<ExamOption, Long>{
	
	@Query("select o from ExamOption o where o.subject.id = :id order by o.sort")
	public List<ExamOption> findAllBySubject(@Param("id") Long id);
	
	@Query("select o from ExamOption o where o.subject.examGroup.exam.id = :id order by o.sort")
	public List<ExamOption> findAllByExam(@Param("id") Long id);
}
