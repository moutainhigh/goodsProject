package com.mendao.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "bu_school_product")
public class SchoolProduct implements java.io.Serializable {

	private static final long serialVersionUID = -2297445333018450643L;
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
	 * 产品（课程）名
	 */
	private String productName;
	/**
	 * 产品（课程）名
	 * @return
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * 产品（课程）名
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 产品（课程）简介
	 */
	@Column(length = 255)
	private String summary;
	/**
	 * 产品（课程）简介
	 * @return
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * 产品（课程）简介
	 * @param summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	/**
	 * 图片地址
	 */
	private String imagePath;

	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * 学校信息
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id")
	private School school;
	/**
	 * 学校信息
	 * @return
	 */
	public School getSchool() {
		return school;
	}
	/**
	 * 学校信息
	 * @param school
	 */
	public void setSchool(School school) {
		this.school = school;
	}
}
