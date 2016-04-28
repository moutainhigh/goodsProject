package com.mendao.business.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.Category;
import com.mendao.business.entity.Sticker;
import com.mendao.business.repository.CategoryRepository;
import com.mendao.business.repository.StickerRepository;
import com.mendao.business.service.StickerService;
import com.mendao.common.util.StringUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.util.CommonUtil;
import com.mendao.util.Util;

@Service("stickerService")
public class StickerServiceImpl implements StickerService{

	@Autowired
	private StickerRepository stickerRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	/**
	 * 根据code查找标签
	 */
	@Override
	public List<Sticker> getStickerByCode(String code) {
		
		return stickerRepository.findStickerByCode(code);
	}
	/**
	 * 根据条件查找标签
	 */
	@Override
	public List<Sticker> getStickerByAttributes(int hot, String code) {
		
		return stickerRepository.StickerByAttributes(hot,code);
	}
	/**
	 * 标签分页数据
	 */
	@Override
	public PageEntity<Sticker> getPage(PageEntity<Sticker> pageEntity) {
		
		return stickerRepository.findByPage(pageEntity);
	}
	/**
	 * 保存标签
	 */
	@Override
	public void save(Sticker sticker) {
		sticker.setSortSeq(CommonUtil.formatSortSeq(sticker.getSortSeq(), 3));
		stickerRepository.save(sticker);
	}
	/**
	 * 根据ID查找标签
	 */
	@Override
	public Sticker findById(Long id) {
		return stickerRepository.findOne(id);
	}
	/**
	 * 根据ID删除标签
	 */
	@Override
	@Transactional
	public void deleteById(Long id) {
		Sticker sticker = stickerRepository.findOne(id);
		Category c = sticker.getCategory();
		String code = c.getType().replaceFirst(sticker.getCode(), "").replaceAll(",,", ",");
		c.setType(StringUtil.trim(code, ","));
		stickerRepository.delete(id);
		categoryRepository.save(c);
	}
	/**
	 * 根据ids查找
	 */
	@Override
	public List<Sticker> findListByIds(String[] ids) {
		String param =Util.getIdSQLParam(ids);
		if (param != null) {
			return stickerRepository.findListByHql("from Sticker t where t.id in (" + param + ")");
		}
		return null;
	}

	@Override
	public List<Sticker> findAllByCode(String code){
		return stickerRepository.findListByHql("from Sticker t where t.code = '".concat(code).concat("'"));
	}
}
