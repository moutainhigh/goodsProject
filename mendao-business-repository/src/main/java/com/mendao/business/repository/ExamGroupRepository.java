package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.ExamGroup;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("examGroupRepository")
public interface ExamGroupRepository extends BaseRepository<ExamGroup, Long>{
	
	@Query("select o from ExamGroup o where o.exam.id = :id order by sort asc")
	public List<ExamGroup> findAllByExam(@Param("id") Long id);
}
