package com.mendao.business.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.Sticker;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("stickerRepository")
public interface StickerRepository extends BaseRepository<Sticker, Long>  {

	@Query("select t from Sticker t where t.code like %:code% order by t.sortSeq asc")
	public List<Sticker> findStickerByCode(@Param("code") String code);

	@Query("select t from Sticker t where t.code like %:code% and t.hot = :hot order by t.sortSeq asc")
	public List<Sticker> StickerByAttributes(@Param("hot") int hot, @Param("code") String code);

	@Query("select t from Sticker t where t.code = :code and t.category.id = :category")
	public Sticker findOneByCodeAndCategory(@Param("code") String code, @Param("category") Long category);
}
