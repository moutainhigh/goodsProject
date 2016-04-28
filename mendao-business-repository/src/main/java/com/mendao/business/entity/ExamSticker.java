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
@Table(name = "cp_exam_sticker")
public class ExamSticker implements java.io.Serializable {

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
	
	@ManyToOne()
	@JoinColumn(name = "report_id")
	private ExamReport report;
	
	@ManyToOne()
	@JoinColumn(name = "sticker_id")
	private Sticker sticker;
	public ExamReport getReport() {
		return report;
	}
	public void setReport(ExamReport report) {
		this.report = report;
	}
	public Sticker getSticker() {
		return sticker;
	}
	public void setSticker(Sticker sticker) {
		this.sticker = sticker;
	}
	
}
