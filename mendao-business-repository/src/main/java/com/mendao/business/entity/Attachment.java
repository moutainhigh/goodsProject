package com.mendao.business.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mendao.common.enums.AttachTypeEnum;

@Entity
@Table(name = "bu_attahment")
public class Attachment implements java.io.Serializable {

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
	 * 附件描述信息
	 */
	@Column(length = 255)
	private String description;
	/**
	 * 附件描述信息
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 附件描述信息
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 附件地址
	 */
	@Column(length = 255, nullable = false)
	private String attachPath;
	/**
	 * 附件地址
	 * @return
	 */
	public String getAttachPath() {
		return attachPath;
	}
	/**
	 * 附件地址
	 * @param attachPath
	 */
	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	/**
	 * 附件分类 
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private AttachTypeEnum attachType;
	/**
	 * 附件分类
	 * @return
	 */
	public AttachTypeEnum getAttachType() {
		return attachType;
	}
	/**
	 * 附件分类
	 * @param attachType
	 */
	public void setAttachType(AttachTypeEnum attachType) {
		this.attachType = attachType;
	}
	
	/**
	 * 文件大小
	 */
	private long fileSize;
	/**
	 * 文件大小
	 * @return
	 */
	public long getFileSize() {
		return fileSize;
	}
	/**
	 * 文件大小
	 * @param fileSize
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * 附件其它属性信息，以Json格式存储。
	 * 例如"{height: 300, width: 300}"
	 */
	@Column(length = 255)
	private String attribute;
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
}
