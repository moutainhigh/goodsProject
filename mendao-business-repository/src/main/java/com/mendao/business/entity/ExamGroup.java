package com.mendao.business.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "cp_exam_group")
public class ExamGroup implements java.io.Serializable {

	private static final long serialVersionUID = -1L;

	@Id
	@GeneratedValue(generator = "identity")
	@GenericGenerator(name = "identity", strategy = "identity")
	@Column(unique = true, nullable = false)
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 分组名
	 */
	@Column(length = 50)
	private String name;
	
	/**
	 * 测评
	 */
	@ManyToOne()
	@JoinColumn(name = "exam_id")
	private Exam exam;
	
	/**
	 * 分组总分
	 */
	private int score; 
	
	/**
	 * 分组排序
	 */
	private int sort;
	
	@OneToMany(mappedBy = "examGroup")
	@OrderBy(value = "sort asc")
	private Set<ExamSubject> subjects;
	
	@OneToMany(mappedBy = "examGroup")
	@OrderBy(value = "sort asc")
	private Set<ExamReport> reports;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
	@OneToMany(mappedBy = "examGroup")
	@OrderBy("sort asc")
	public Set<ExamSubject> getSubjects() {
		return subjects;
	}
	public void setSubjects(Set<ExamSubject> subjects) {
		this.subjects = subjects;
	}
	
	@OneToMany(mappedBy = "examGroup")
	@OrderBy("sort asc")
	public Set<ExamReport> getReports() {
		return reports;
	}
	public void setReports(Set<ExamReport> reports) {
		this.reports = reports;
	}
	
}
