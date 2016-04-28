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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "co_topic_comment")
public class TopicComment implements java.io.Serializable {

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
	 * 评论父评论
	 */
	@ManyToOne(cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", nullable = true)
	private TopicComment parent;
	
	public TopicComment getParent() {
		return parent;
	}
	public void setParent(TopicComment parent) {
		this.parent = parent;
	}

	/**
	 * 评论内容
	 */
	@Column(length = 500, nullable = false)
	private String content;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 评论时间
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
	 * 引用的评论ID
	 */
	private Long referenc;
	/**
	 * 引用的评论ID
	 * @return
	 */
	public Long getReferenc() {
		return referenc;
	}
	/**
	 * 引用的评论ID
	 * @param referenc
	 */
	public void setReferenc(Long referenc) {
		this.referenc = referenc;
	}
	
	/**
	 * 引用曾经深度
	 */
	private int referLever;
	/**
	 * 引用曾经深度
	 * @return
	 */
	public int getReferLever() {
		return referLever;
	}
	/**
	 * 引用曾经深度
	 * @param referLever
	 */
	public void setReferLever(int referLever) {
		this.referLever = referLever;
	}
	
	/**
	 * 评论人
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
	 * 评论的引用路径(格式为1_2_3)
	 */
	@Column(length = 255, nullable = true)
	private String referPath;
	
	public String getReferPath() {
		return referPath;
	}
	public void setReferPath(String referPath) {
		this.referPath = referPath;
	}
	
	
}
