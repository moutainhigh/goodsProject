package com.mendao.business.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "cp_exam")
public class Exam implements java.io.Serializable {

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
	 * 标题
	 */
	@Column(length = 100)
	private String title;
	
	/**
	 * 简介
	 */
	@Column(length = 500)
	private String summary;
	
	/**
	 * 适用对象
	 */
	@Column(length = 50)
	private String scope;
	
	/**
	 * 缩略图
	 */
	@Column(length = 255)
	private String icon;
	
	/**
	 * 图片地址
	 */
	@Column(length = 255)
	private String image;
	
	/**
	 * 测评类型
	 */
	private int type;
	
	private int sort;
	
	/**
	 * 状态。0:删除；1:正常
	 */
	private int status;
	
	/**
	 * 显示报告个数
	 */
	private int reportLength = 1;
	
	/**
	 * 追述
	 */
	@Column(length = 500)
	private String content;
	
	@OneToMany(mappedBy = "exam")
	@OrderBy(value = "sort asc")
	private Set<ExamGroup> examGroups;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Set<ExamGroup> getExamGroups() {
		return examGroups;
	}
	public void setExamGroups(Set<ExamGroup> examGroups) {
		this.examGroups = examGroups;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getReportLength() {
		return reportLength;
	}
	public void setReportLength(int reportLength) {
		this.reportLength = reportLength;
	}
}
