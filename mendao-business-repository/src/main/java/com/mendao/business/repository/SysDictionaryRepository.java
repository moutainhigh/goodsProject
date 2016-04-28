package com.mendao.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.business.entity.SysDictionary;
import com.mendao.framework.base.jpa.BaseRepository;

@Repository("dictionaryRepository")
public interface SysDictionaryRepository extends BaseRepository<SysDictionary, Long>{
	@Query("select o from SysDictionary o where o.dicCode = :code")
	public List<SysDictionary> findListByCode(@Param("code") String code);
}
