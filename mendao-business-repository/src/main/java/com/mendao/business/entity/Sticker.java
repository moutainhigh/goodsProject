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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

/**
 * 分类标签
 * @author libra
 *
 */
@Entity
@Table(name = "bu_sticker")
public class Sticker  implements java.io.Serializable {

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
	 * 分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
	/**
	 * 分类
	 * @return
	 */
	public Category getCategory() {
		return category;
	}
	/**
	 * 分类
	 * @param category
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * 标签分类（使用对象类型名作为分类）
	 */
	@Column(length = 30, nullable = false)
	private String code;
	/**
	 * 标签分类（使用对象类型名作为分类）
	 * @return
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 标签分类（使用对象类型名作为分类）
	 * @param category
	 */
	public void setCode(String code) {
		this.code = code;
	}



	/**
	 * 标签内容
	 */
	@Column(length = 20, nullable = false)
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
	 * 标签排序
	 */
	@Column(length = 20)
	private String sortSeq;
	
	public String getSortSeq() {
		return sortSeq;
	}
	public void setSortSeq(String sortSeq) {
		this.sortSeq = sortSeq;
	}

	/**
	 * 标签是否为热门标签
	 * 1:是 0:否
	 */
	@Column(length = 11 ,nullable = false)
	@ColumnDefault(value = "0")
	private int hot;
	
	public int getHot() {
		return hot;
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	
	
	public static final String SCHOOL_STICKER = "SCHOOL_STICKER";
	public static final String ACTIVITY_STICKER = "ACTIVITY_STICKER";
	public static final String TOPIC_STICKER = "TOPIC_STICKER";
}
