package com.mendao.business.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
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
import org.hibernate.annotations.Type;
/**
 * 活动表
 * @author warden
 *
 */
@Entity
@Table(name = "co_activity")
public class Activity implements java.io.Serializable {

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
	 * 活动名称
	 */
	@Column(length = 100, nullable = false)
	private String name;
	/**
	 * 发布者姓名
	 */
	@Column(length = 100, nullable = true)
	private String publishName;
	
	/**
	 * 活动内容
	 */
	@Column(nullable = false)
	@Type(type = "text")
	private String content;
	/**
	 * 活动发布时间
	 */
	private Date pubTime;

	/**
	 * 活动开始时间
	 */
	private Timestamp startTime;

	/**
	 * 活动结束时间
	 */
	private Timestamp endTime;

	/**
	 * 活动类型 0:当天结束 1:多天活动
	 */
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private int type;

	/**
	 * 是否通过审核。0:否；1:是
	 */
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private int examined;

	/**
	 * 状态。0:正常；－1:已删除
	 */
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private int status;

	/**
	 * 活动地址
	 */
	@Column(nullable = true)
	private String address;
	
	/**
	 * 发布活动的用户
	 */
	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	private UserInfo author;

	/**
	 * 活动图片
	 */
	@Column(length = 255, nullable = true)
	private String activityImage;

	/**
	 * 活动阅读数量
	 */
	@Column(length = 11, nullable = false)
	@ColumnDefault(value = "0")
	private int views;

	/**
	 * 活动评论数量
	 */
	@Column(length = 11, nullable = false)
	@ColumnDefault(value = "0")
	private int commentNumber;

	/**
	 * 活动参加报名人数
	 */
	@Column(length = 11, nullable = false)
	@ColumnDefault(value = "0")
	private int joinNumber;

	/**
	 * 是否推荐到首页 0:否 1:是
	 */
	@Column(length = 11, nullable = false)
	@ColumnDefault(value = "0")
	private int recommend;

	/**
	 * 活动修改时间
	 */
	@Column(nullable = true)
	private Date modifiedDate;
	
	/**
	 * 活动规模
	 */
	@Column(length = 11, nullable = false)
	@ColumnDefault(value = "0")
	private String size;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublishName() {
		return publishName;
	}

	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getExamined() {
		return examined;
	}

	public void setExamined(int examined) {
		this.examined = examined;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserInfo getAuthor() {
		return author;
	}

	public void setAuthor(UserInfo author) {
		this.author = author;
	}

	public String getActivityImage() {
		return activityImage;
	}

	public void setActivityImage(String activityImage) {
		this.activityImage = activityImage;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(int commentNumber) {
		this.commentNumber = commentNumber;
	}

	public int getJoinNumber() {
		return joinNumber;
	}

	public void setJoinNumber(int joinNumber) {
		this.joinNumber = joinNumber;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
