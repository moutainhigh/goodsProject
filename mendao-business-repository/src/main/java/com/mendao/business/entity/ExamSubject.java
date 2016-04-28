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
@Table(name = "cp_exam_subject")
public class ExamSubject implements java.io.Serializable {

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
	 * 问题描述
	 */
	@Column(length = 500)
	private String question;
	
	/**
	 * 题型。0:选择题；1:判断题
	 */
	private int type;
	
	@ManyToOne()
	@JoinColumn(name = "group_id")
	private ExamGroup examGroup;
	
	private int sort;
	
	/**
	 * 选项集合
	 */
	@OneToMany(mappedBy = "subject")
	@OrderBy("sort asc")
	private Set<ExamOption> options;
	

	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public ExamGroup getExamGroup() {
		return examGroup;
	}
	public void setExamGroup(ExamGroup examGroup) {
		this.examGroup = examGroup;
	}
	
	@OneToMany(mappedBy = "subject")
	@OrderBy("sort asc")
	public Set<ExamOption> getOptions() {
		return options;
	}
	public void setOptions(Set<ExamOption> options) {
		this.options = options;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
