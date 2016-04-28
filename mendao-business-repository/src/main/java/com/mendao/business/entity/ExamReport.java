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
@Table(name = "cp_exam_report")
public class ExamReport implements java.io.Serializable {

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
	 * 分组
	 */
	@ManyToOne()
	@JoinColumn(name = "group_id")
	private ExamGroup examGroup;

	/**
	 * 分值上限
	 */
	private int maxScore; 
	
	/**
	 * 分值下限
	 */
	private int minScore; 
	
	
	/**
	 * 分组排序
	 */
	private int sort;
	
	@Column(length = 500)
	private String content;
	
	@OneToMany(mappedBy = "report")
	private Set<ExamSticker> stickers;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ExamGroup getExamGroup() {
		return examGroup;
	}
	public void setExamGroup(ExamGroup examGroup) {
		this.examGroup = examGroup;
	}
	public int getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}
	public int getMinScore() {
		return minScore;
	}
	public void setMinScore(int minScore) {
		this.minScore = minScore;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Set<ExamSticker> getStickers() {
		return stickers;
	}
	public void setStickers(Set<ExamSticker> stickers) {
		this.stickers = stickers;
	}
	
}
