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
/**
 * 我收藏的话题
 * @author warden
 *
 */
@Entity
@Table(name = "co_topic_collection")
public class TopicCollection implements java.io.Serializable {

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
	 * 话题信息
	 */
	@ManyToOne(cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "topic_id", nullable = false)
	private Topic topic;
	/**
	 * 话题信息
	 * @return
	 */
	public Topic getTopic() {
		return topic;
	}
	/**
	 * 话题信息
	 * @param topic
	 */
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	/**
	 * 收藏人
	 */
	@ManyToOne(cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	private UserInfo author;
	/**
	 * 评论人
	 * @return
	 */
	public UserInfo getAuthor() {
		return author;
	}
	/**
	 * 评论人
	 * @param author
	 */
	public void setAuthor(UserInfo author) {
		this.author = author;
	}
	/**
	 * 收藏时间
	 */
	@Column(nullable = false)
	private Date createdDate;
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
}
