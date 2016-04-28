package com.mendao.business.entity;

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

@Entity
@Table(name = "co_topic")
public class Topic implements java.io.Serializable {

	/**
	 *
	 */
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
	 * 话题标题
	 */
	@Column(length = 100, nullable = false)
	private String subject;
	/**
	 * 话题标题
	 * @return
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * 话题标题
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * 话题内容
	 */
	@Column(nullable = false)
	@Type(type = "text")
	private String content;
	/**
	 * 话题内容
	 * @return
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 话题内容
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 创建时间
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 创建时间
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * 发布时间
	 */
	private Date pubDate;
	/**
	 * 发布时间
	 * @return
	 */
	public Date getPubDate() {
		return pubDate;
	}
	/**
	 * 发布时间
	 * @param pubDate
	 */
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	
	/**
	 * 是否通过审核。0:否；1:是
	 */
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private int examined;
	/**
	 * 是否通过审核。0:否；1:是
	 * @return
	 */
	public int getExamined() {
		return examined;
	}
	/**
	 * 是否通过审核。0:否；1:是
	 * @param examined
	 */
	public void setExamined(int examined) {
		this.examined = examined;
	}
	
	/**
	 * 状态。0:正常；－1:已删除
	 */
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private int status;
	/**
	 * 状态。0:正常；－1:已删除
	 * @return
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * 状态。0:正常；－1:已删除
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * 发帖人
	 */
	@ManyToOne(cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	private UserInfo author;
	/**
	 * 发帖人
	 * @return
	 */
	public UserInfo getAuthor() {
		return author;
	}
	/**
	 * 发帖人
	 * @param author
	 */
	public void setAuthor(UserInfo author) {
		this.author = author;
	}

	/**
	 * 话题图片
	 */
	@Column(length = 255, nullable = true)
	private String topicImage;
	public String getTopicImage() {
		return topicImage;
	}
	public void setTopicImage(String topicImage) {
		this.topicImage = topicImage;
	}

	/**
	 * 话题阅读数量
	 */
	@Column(length = 11, nullable = false)
	@ColumnDefault(value = "0")
	private int views;
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}

	/**
	 * 话题评论数量
	 */
	@Column(length = 11, nullable = false)
	@ColumnDefault(value = "0")
	private int commentNumber;
	public int getCommentNumber() {
		return commentNumber;
	}
	public void setCommentNumber(int commentNumber) {
		this.commentNumber = commentNumber;
	}

	/**
	 * 话题点赞数量
	 */
	@Column(length = 11, nullable = false)
	@ColumnDefault(value = "0")
	private int ups;
	public int getUps() {
		return ups;
	}
	public void setUps(int ups) {
		this.ups = ups;
	}
	/**
	 * 置顶排序  0:未置顶  1:已经置顶   
	 */
	@Column(length = 11 ,nullable = false)
	@ColumnDefault(value = "0")
	private int top;
	
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	/**
	 * 加精排序  0:未加精  1:已加精
	 */
	@Column(length = 11 ,nullable = false)
	@ColumnDefault(value = "0")
	private int competitive = 0;
	
	public int getCompetitive() {
		return competitive;
	}
	public void setCompetitive(int competitive) {
		this.competitive = competitive;
	}
	/**
	 * 话题修改时间
	 */
	@Column(nullable = true)
	private Date modifiedDate;
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
