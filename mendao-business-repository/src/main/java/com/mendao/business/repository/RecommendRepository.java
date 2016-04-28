package com.mendao.business.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.Recommend;
import com.mendao.business.entity.School;
import com.mendao.framework.base.jpa.BaseRepository;
@Repository("recommendRepository")
public interface RecommendRepository extends BaseRepository<Recommend, Long>  {

	@Query("select t from Recommend t where t.type = :type order by t.id desc")
	public List<Recommend> getIdListByType(@Param("type") int type);

	@Query("select t.school from Recommend t where t.type = :type and t.category.id = :categoryId order by t.id desc")
	public List<School> getSchoolListByAttributes(@Param("type") int type,@Param("categoryId")  Long categoryId);

   

}
