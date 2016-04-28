package com.mendao.business.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

/**
 * 广告表
 * 
 * @author warden
 *
 */
@Entity
@Table(name = "bu_advert")
public class Advert implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1411805770506061037L;

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
	 * 广告名称
	 */
	@Column(length = 100, nullable = false)
	private String name;

	/**
	 * 广告创建时间
	 */
	private Date createdTime;

	/**
	 * 状态。0:正常；－1:已删除
	 */
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private int status;
	/**
	 * 广告位置
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place", nullable = true)
	private SysDictionary place;

	/**
	 * 广告图片
	 */
	@Column(length = 255, nullable = true)
	private String imagePath;

	/**
	 * 广告链接
	 */
	@Column(length = 255, nullable = true)
	private String linkPath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public SysDictionary getPlace() {
		return place;
	}

	public void setPlace(SysDictionary place) {
		this.place = place;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getLinkPath() {
		return linkPath;
	}

	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
	}

}
