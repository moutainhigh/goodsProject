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
@Table(name = "bu_category")
public class Category implements java.io.Serializable {

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
	 * 类别编码（使用对象类型名作为分类）
	 */
	@Column(length = 30, nullable = false)
	private String code;
	/**
	 * 类别编码（使用对象类型名作为分类）
	 * @return
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 类别编码（使用对象类型名作为分类）
	 * @param category
	 */
	public void setCode(String code) {
		this.code = code;
	}

	
	/**
	 * 标签内容
	 */
	@Column(length = 20)
	private String display;
	/**
	 * 标签内容
	 * @return
	 */
	public String getDisplay() {
		return display;
	}
	/**
	 * 标签内容
	 * @param display
	 */
	public void setDisplay(String display) {
		this.display = display;
	}
	
	/**
	 * 父标签
	 */
	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Category parent;
	/**
	 * 父标签
	 * @return
	 */
	public Category getParent() {
		return parent;
	}
	/**
	 * 父标签
	 * @param parent
	 */
	public void setParent(Category parent) {
		this.parent = parent;
	}



	/**
	 * 树深度
	 */
	private Integer treeLevel;
	/**
	 * 树深度
	 * @return
	 */
	public Integer getTreeLevel() {
		return treeLevel;
	}
	/**
	 * 树深度
	 * @param treeLevel
	 */
	public void setTreeLevel(Integer treeLevel) {
		this.treeLevel = treeLevel;
	}



	/**
	 * 树目录
	 */
	@Column(length = 100)
	private String treePath;
	/**
	 * 树目录
	 */
	public String getTreePath() {
		return treePath;
	}
	/**
	 * 树目录
	 * @param treePath
	 */
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}


	/**
	 * 排序号
	 */
	@Column(length = 50)
	private String sortSeq;
	/**
	 * 排序号
	 * @return
	 */
	public String getSortSeq() {
		return sortSeq;
	}
	/**
	 * 排序号
	 * @param sortNo
	 */
	public void setSortSeq(String sortSeq) {
		this.sortSeq = sortSeq;
	}
	
	@Column(length = 100)
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNodeName(){
		return this.display;
	}
	
	public Category(){
	}
}
