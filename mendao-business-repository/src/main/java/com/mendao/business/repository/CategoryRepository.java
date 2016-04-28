package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.Category;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("categoryRepository")
public interface CategoryRepository extends BaseRepository<Category, Long>{
	@Query("select t from Category t order by t.sortSeq")
	public List<Category> findAllCategories();
	
	@Query("select t from Category t where t.code = :code order by t.sortSeq")
	public List<Category> findAllByCode(@Param("code") String code);

	@Query("select t from Category t where t.code = :code and treeLevel = :treeLevel and type like %:type% order by t.sortSeq")
	public List<Category> getListByAttributes(@Param("code")String code, @Param("treeLevel") Integer treeLevel, @Param("type")String type);
	
}
