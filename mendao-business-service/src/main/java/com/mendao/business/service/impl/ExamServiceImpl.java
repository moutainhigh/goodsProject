package com.mendao.business.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.dto.GrowthReport;
import com.mendao.business.entity.Category;
import com.mendao.business.entity.Exam;
import com.mendao.business.entity.ExamGroup;
import com.mendao.business.entity.ExamOption;
import com.mendao.business.entity.ExamReport;
import com.mendao.business.entity.ExamSticker;
import com.mendao.business.entity.ExamSubject;
import com.mendao.business.entity.Sticker;
import com.mendao.business.repository.ExamGroupRepository;
import com.mendao.business.repository.ExamOptionRepository;
import com.mendao.business.repository.ExamReportRepository;
import com.mendao.business.repository.ExamRepository;
import com.mendao.business.repository.ExamStickerRepository;
import com.mendao.business.repository.ExamSubjectRepository;
import com.mendao.business.service.ExamService;
import com.mendao.common.util.StringUtil;
import com.mendao.framework.base.jpa.PageEntity;

@Service("examService")
public class ExamServiceImpl extends BaseServiceImpl<Exam, Long> implements ExamService{

	@Autowired
	ExamRepository examRepository;
	
	@Autowired
	ExamGroupRepository examGroupRepository;
	
	@Autowired
	ExamSubjectRepository examSubjectRepository;
	
	@Autowired
	ExamOptionRepository examOptionRepository;
	
	@Autowired
	ExamReportRepository examReportRepository;
	
	@Autowired
	ExamStickerRepository examStickerRepository;
	
	@Override
	@Transactional
	public void mergeGroups(Set<ExamGroup> groups){
		groups.forEach(p->{
			examGroupRepository.merge(p);
		});
	}
	
	@Override
	@Transactional
	public void mergeSubjects(Set<ExamSubject> subjects){
		subjects.forEach(p->{
			examSubjectRepository.merge(p);
		});
	}
	
	@Override
	@Transactional
	public void mergeOptions(Set<ExamOption> options){
		options.forEach(p->{
			examOptionRepository.merge(p);
		});
	}
	
	@Override
	@Transactional
	public void mergeReports(Set<ExamReport> reports){
		reports.forEach(p->{
			examReportRepository.merge(p);
		});
	}
	
	@Override
	@Transactional
	public void mergeStickers(Long reportId, Set<ExamSticker> stickers){
		ExamReport report = examReportRepository.findOne(reportId);
		report.getStickers().forEach(p->{
			examStickerRepository.delete(p);
		});
		stickers.forEach(p->{
			examStickerRepository.merge(p);
		});
	}
	
	@Override
	public List<ExamOption> findOptionsBySubject(Long id){
		return examOptionRepository.findAllBySubject(id);
	}
	
	@Override
	public List<ExamReport> findReportsByExam(Long id){
		return examReportRepository.findAllByExam(id);
	}
	
	@Override
	public List<ExamGroup> findGroupsByExam(Long id){
		return examGroupRepository.findAllByExam(id);
	}
	
	@Override
	public List<ExamSubject> findSubjectsByExam(Long id){
		return examSubjectRepository.findListByHql("select o from ExamSubject o where o.examGroup.exam.id = " + id + " order by o.sort");
	}
	
	@Override
	public List<ExamOption> findOptionsByExam(Long id){
		return examOptionRepository.findAllByExam(id);
	}
	
	@Override
	public ExamSubject findOneSubject(Long id){
		return examSubjectRepository.findOne(id);
	}
	
	@Override
	public ExamGroup findOneGroup(Long id){
		return examGroupRepository.findOne(id);
	}
	
	@Override
	public PageEntity<Exam> findByPage(PageEntity<Exam> pageBean){
		return examRepository.findByPage(pageBean);
	}
	
	@Override
	public List<ExamSticker> findStickerByReport(Long id){
		return examStickerRepository.findListByHql("select o from ExamSticker o where o.report.id = " + id);
	}
	
	@Override
	public GrowthReport examProcess(GrowthReport report){
		Exam exam = examRepository.findOne(report.getId());
		long limitSize = exam.getReportLength();
		StringBuffer sql = new StringBuffer();
		sql.append("select r.* from cp_exam_report r join ");
		sql.append("(select g.id, sum(o.score) as total "
				+ "from cp_exam_group g "
				+ "join cp_exam_subject s on s.group_id = g.id "
				+ "join cp_exam_option o on o.subject_id = s.id "
				+ "where o.id in ("
				+ report.getOptions().parallelStream().reduce((out, p)-> {
						if(StringUtil.isNotBlank(p)) return out.concat(",").concat(p);
						else return out;
					}).get() 
				+ ") group by g.id) t on r.group_id = t.id where r.min_score <= t.total and r.max_score >= t.total ");
		sql.append("order by t.total desc, r.sort");
		List<ExamReport> reports = examReportRepository.findAllBySql(ExamReport.class, sql.toString());
		Set<ExamReport> stream = reports.stream().limit(limitSize).collect(Collectors.toSet());
		report.setContent(stream.stream().map(ExamReport::getContent).collect(Collectors.toList()));
		if(StringUtil.isNotBlank(exam.getContent())){
			report.getContent().add(exam.getContent());
		}
		Set<Sticker> repStickers = new HashSet<Sticker>();
		stream.stream().forEach(p->{
			if(!p.getStickers().isEmpty()){
				repStickers.addAll(p.getStickers().stream().map(ExamSticker::getSticker).collect(Collectors.toSet()));
			}
		});
		if(!repStickers.isEmpty()){
			Map<Long, String> stickers = repStickers.stream().distinct().map(Sticker::getCategory)
					.collect(Collectors.toMap(Category::getId, Category::getDisplay));
			report.setStickers(stickers);
		}
		return report;
	}
	
	@Override
	@Transactional
	public void deleteExam(Long id){
		examRepository.delete(id);
	}
	
	@Override
	@Transactional
	public void deleteExam(List<Long> ids){
		Iterable<Exam> exams = examRepository.findAll(ids);
		Set<ExamGroup> groups = new HashSet<ExamGroup>();
		Set<ExamSubject> subjects = new HashSet<ExamSubject>();
		Set<ExamReport> reports = new HashSet<ExamReport>();
		exams.forEach(p -> {
			groups.addAll(p.getExamGroups());
			examGroupRepository.delete(p.getExamGroups());
			examRepository.delete(p);
		});
		groups.forEach(p -> {
			subjects.addAll(p.getSubjects());
			reports.addAll(p.getReports());
			
			examSubjectRepository.delete(p.getSubjects());
			examReportRepository.delete(p.getReports());
		});
		subjects.forEach(p -> {
			examOptionRepository.delete(p.getOptions());
		});
		
		reports.forEach(p -> {
			examStickerRepository.delete(p.getStickers());
		});
	}
}
