package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.Category;
import com.mendao.framework.base.jpa.PageEntity;

public interface CategoryService extends BaseService<Category, Long>{

	List<Category> getAllCategoryByCode(String code);

	void save(Category category);

	PageEntity<Category> getPage(PageEntity<Category> pageBean);

	List<Category> getListByAttributes(String code, Integer treeLevel,String type);

	List<Category> getListByLevelAndParent(Long parent, int level,String type);
}
