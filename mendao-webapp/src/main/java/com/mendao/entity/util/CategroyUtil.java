package com.mendao.entity.util;

import java.util.List;

import com.mendao.business.entity.Category;

public class CategroyUtil {
	/**
	 * 标签
	 */
	private Category category;
	/**
	 * 子标签List
	 */
	private List<CategroyChildUtil> children;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<CategroyChildUtil> getChildren() {
		return children;
	}

	public void setChildren(List<CategroyChildUtil> children) {
		this.children = children;
	}

}
