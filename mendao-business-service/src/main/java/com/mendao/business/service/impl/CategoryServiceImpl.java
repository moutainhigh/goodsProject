package com.mendao.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.Category;
import com.mendao.business.entity.Sticker;
import com.mendao.business.repository.CategoryRepository;
import com.mendao.business.repository.StickerRepository;
import com.mendao.business.service.CategoryService;
import com.mendao.common.util.StringUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.util.CommonUtil;


@Service("categoryService")
public class CategoryServiceImpl extends BaseServiceImpl<Category, Long> implements CategoryService {

	@Autowired
	CategoryRepository categoryRep;
	@Autowired
	StickerRepository stickerRep;
	
	@Override
	public List<Category> getAllCategoryByCode(String code) {
		return categoryRep.findAllCategories();
	}

	@Override
	public void save(Category category){
		if(null == category.getParent() || null == category.getParent().getId()){
			category.setParent(null);
			category.setTreeLevel(1);
		}else{
			Category parent = categoryRep.findOne(category.getParent().getId());
			category.setTreeLevel(parent.getTreeLevel() + 1);
			String treePath = StringUtil.isBlank(parent.getTreePath()) ? parent.getId() + "" : parent.getTreePath() + "," + parent.getId();
			category.setTreePath(treePath);
			category.setSortSeq(parent.getSortSeq() + "." + category.getSortSeq());
		}
		//*************************************************
		//* 生成排序字段。格式为"{###}.{###}.{###}.{###}"。
		//*************************************************
		category.setSortSeq(CommonUtil.formatSortSeq(category.getSortSeq(), 3));
		categoryRep.save(category);
		//initSticker(category);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtil.defaultIfBlank(category.getType()).indexOf("ACTIVITY_STICKER") > -1){
			addStickerIfNotExists(category, "ACTIVITY_STICKER");
		}else{
			Sticker sticker = stickerRep.findOneByCodeAndCategory("ACTIVITY_STICKER", category.getId());
			if(sticker != null){
				stickerRep.delete(sticker.getId());
			}
		}
		
		params.clear();
		if(StringUtil.defaultIfBlank(category.getType()).indexOf("TOPIC_STICKER") > -1){
			addStickerIfNotExists(category, "TOPIC_STICKER");
		}else{
			Sticker sticker = stickerRep.findOneByCodeAndCategory("TOPIC_STICKER", category.getId());
			if(sticker != null){
				stickerRep.delete(sticker.getId());
			}
		}
		
		params.clear();
		if(StringUtil.defaultIfBlank(category.getType()).indexOf("SCHOOL_STICKER") > -1){
			addStickerIfNotExists(category, "SCHOOL_STICKER");
		}else{
			Sticker sticker = stickerRep.findOneByCodeAndCategory("SCHOOL_STICKER", category.getId());
			if(sticker != null){
				stickerRep.delete(sticker.getId());
			}
		}
		
		params.clear();
		if(StringUtil.defaultIfBlank(category.getType()).indexOf("FOLLOW_STICKER") > -1){
			addStickerIfNotExists(category, "FOLLOW_STICKER");
		}else{
			Sticker sticker = stickerRep.findOneByCodeAndCategory("FOLLOW_STICKER", category.getId());
			if(sticker != null){
				stickerRep.delete(sticker.getId());
			}
		}
	}
	
	@Override
	public PageEntity<Category> getPage(PageEntity<Category> pageBean){
		pageBean = categoryRep.findByPage(pageBean);
		for(Category c : pageBean.getResult()){
			c.getParent();
		}
		return categoryRep.findByPage(pageBean);
	}
	
	protected void initSticker(Category category){
		
	}
	
	private void addStickerIfNotExists(Category category, String code){
		Sticker sticker = stickerRep.findOneByCodeAndCategory(code, category.getId());
		if(sticker == null){
			sticker = new Sticker();
			sticker.setCategory(category);
			sticker.setCode(code);
			sticker.setDisplay(category.getDisplay());
			stickerRep.save(sticker);
		}else{
			sticker.setDisplay(category.getDisplay());
			stickerRep.save(sticker);
		}
	}

	@Override
	public List<Category> getListByAttributes(String code, Integer treeLevel, String type) {
		return categoryRep.getListByAttributes(code,treeLevel,type);
	}

	@Override
	public List<Category> getListByLevelAndParent(Long parent, int level, String type) {
		return categoryRep.findListByHql("from Category c where c.parent.id = "+parent+" and c.treeLevel = "+level+" and c.type like '%"+type+"%' order by c.sortSeq");
	}
}
