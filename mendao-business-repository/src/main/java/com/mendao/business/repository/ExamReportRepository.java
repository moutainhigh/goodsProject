package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.ExamReport;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("examReportRepository")
public interface ExamReportRepository extends BaseRepository<ExamReport, Long>{
	
	@Query("select o from ExamReport o where o.examGroup.exam.id = :id order by sort asc")
	public List<ExamReport> findAllByExam(@Param("id") Long id);
}
