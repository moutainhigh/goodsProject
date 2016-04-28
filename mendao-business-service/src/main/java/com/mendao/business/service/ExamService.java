package com.mendao.business.service;

import java.util.List;
import java.util.Set;

import com.mendao.business.dto.GrowthReport;
import com.mendao.business.entity.Exam;
import com.mendao.business.entity.ExamGroup;
import com.mendao.business.entity.ExamOption;
import com.mendao.business.entity.ExamReport;
import com.mendao.business.entity.ExamSticker;
import com.mendao.business.entity.ExamSubject;
import com.mendao.framework.base.jpa.PageEntity;

public interface ExamService extends BaseService<Exam, Long>{

	void mergeGroups(Set<ExamGroup> groups);

	void mergeSubjects(Set<ExamSubject> subjects);

	List<ExamOption> findOptionsBySubject(Long id);

	void mergeOptions(Set<ExamOption> options);

	ExamSubject findOneSubject(Long id);

	void mergeReports(Set<ExamReport> reports);

	ExamGroup findOneGroup(Long id);

	List<ExamReport> findReportsByExam(Long id);

	List<ExamGroup> findGroupsByExam(Long id);

	PageEntity<Exam> findByPage(PageEntity<Exam> pageBean);

	List<ExamSubject> findSubjectsByExam(Long id);

	List<ExamOption> findOptionsByExam(Long id);

	GrowthReport examProcess(GrowthReport report);

	List<ExamSticker> findStickerByReport(Long id);

	void mergeStickers(Long reportId, Set<ExamSticker> stickers);

	void deleteExam(Long id);

	void deleteExam(List<Long> ids);

}
