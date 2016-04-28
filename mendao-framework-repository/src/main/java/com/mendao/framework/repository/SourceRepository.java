package com.mendao.framework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mendao.framework.base.jpa.BaseRepository;
import com.mendao.framework.entity.FwSource;
@Repository("sourceRepository")
public interface SourceRepository extends BaseRepository<FwSource, Long>  {

    @Query("select t from FwSource t where t.status=1 order by t.sortSeq ASC")
    public List<FwSource> findAllFwSource();
    
    @Query("select t from FwSource t where t.status=1 and t.treePath=:treePath order by t.sortSeq ASC")
    public List<FwSource> findSiblings(@Param("treePath") String treePath);
    
    @Query("select t from FwSource t where t.status=1 and t.treePath like :treePath order by t.sortSeq ASC")
    public List<FwSource> findPosterity(@Param("treePath") String treePath);

}
