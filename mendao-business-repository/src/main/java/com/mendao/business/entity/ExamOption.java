package com.mendao.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "cp_exam_option")
public class ExamOption implements java.io.Serializable {

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
	 * 题目
	 */
	@ManyToOne()
	@JoinColumn(name = "subject_id")
	private ExamSubject subject;

	/**
	 * 标识
	 */
	@Column(length = 5)
	private String code;
	
	/**
	 * 选项内容
	 */
	@Column(length = 200)
	private String text;
	
	/**
	 * 选中得分
	 */
	private int score;
	
	/**
	 * 排序
	 */
	private int sort;
	
	public ExamSubject getSubject() {
		return subject;
	}
	public void setSubject(ExamSubject subject) {
		this.subject = subject;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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
	
}
