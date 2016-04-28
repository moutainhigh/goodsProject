package com.mendao.framework.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "fw_source")
public class FwSource implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5175246456181078279L;
	@Id
    @GeneratedValue(generator = "identity")
    @GenericGenerator(name = "identity", strategy = "identity")
    @Column(unique = true, nullable = false)
	private Long id ;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 菜单名
	 */
	@Column(length = 50, nullable = false)
	private String sourceName ;
	/**
	 * 菜单名
	 * @return
	 */
	public String getSourceName() {
		return sourceName;
	}
	/**
	 * 菜单名
	 * @param name
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	/**
	 * 菜单对应的action url。若无url，则默认为"#"
	 */
	@Column(length = 255)
	private String sourceAction;
	/**
	 * 菜单对应的action url。若无url，则默认为"#"
	 * @return
	 */
	public String getSourceAction() {
		return sourceAction;
	}
	/**
	 * 菜单对应的action url。若无url，则默认为"#"
	 * @param sourceCode
	 */
	public void setSourceAction(String sourceAction) {
		this.sourceAction = sourceAction;
	}
	

	/**
	 * 状态。0:不可用；1:可用
	 */
	private int status;
	/**
	 * 状态。0:不可用；1:可用
	 * @return
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * 状态。0:不可用；1:可用
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	/**
	 * 描述
	 */
	@Column(length = 255)
	private String description;
	/**
	 * 描述
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 描述
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
	/**
	 * 图标
	 */
	@Column(length = 100)
	private String sourceIcon ;
	/**
	 * 图标
	 * @return
	 */
	public String getSourceIcon() {
		return sourceIcon;
	}
	/**
	 * 图标
	 * @param sourceIcon
	 */
	public void setSourceIcon(String sourceIcon) {
		this.sourceIcon = sourceIcon;
	}
	
	
	
	/**
	 * 菜单树形深度
	 */
	private int treeLevel;
	/**
	 * 菜单树形深度
	 * @return
	 */
	public int getTreeLevel() {
		return treeLevel;
	}
	/**
	 * 菜单树形深度
	 * @param level
	 */
	public void setTreeLevel(int treeLevel) {
		this.treeLevel = treeLevel;
	}

	
	/**
	 * 树形目录
	 */
	@Column(length = 100)
	private String treePath;
	/**
	 * 树形目录
	 * @return
	 */
	public String getTreePath() {
		return treePath;
	}
	/**
	 * 树形目录
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

	

	/**
	 * 父节点
	 */
	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private FwSource parent ;
	/**
	 * 父节点
	 * @return
	 */
	public FwSource getParent() {
		return parent;
	}
	/**
	 * 父节点
	 * @param parent
	 */
	public void setParent(FwSource parent) {
		this.parent = parent;
	}
	
	/**
	 * 是否为菜单项。0:权限；1:菜单
	 */
	private int isMenu;
	public int getIsMenu() {
		return isMenu;
	}
	public void setIsMenu(int isMenu) {
		this.isMenu = isMenu;
	}

	/** default constructor */
	public FwSource() {
		this.status = 1;
		this.isMenu = 0;
		this.sortSeq = "";
		this.treeLevel = 1;
	}
}
