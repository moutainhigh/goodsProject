package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.Sticker;
import com.mendao.framework.base.jpa.PageEntity;

public interface StickerService {

	List<Sticker> getStickerByCode(String string);

	List<Sticker> getStickerByAttributes(int hot, String code);

	PageEntity<Sticker> getPage(PageEntity<Sticker> pageEntity);

	void save(Sticker sticker);

	Sticker findById(Long id);

	void deleteById(Long id);

	List<Sticker> findListByIds(String[] ids);

	List<Sticker> findAllByCode(String code);
}
