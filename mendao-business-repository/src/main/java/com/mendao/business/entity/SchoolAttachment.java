package com.mendao.business.entity;

import javax.persistence.CascadeType;
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
@Table(name = "bu_school_attachment")
public class SchoolAttachment implements java.io.Serializable {

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
 	 * 学校
 	 */
 	@ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name = "school_id")
 	private School school;
 
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}

	private String imagePath;
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
