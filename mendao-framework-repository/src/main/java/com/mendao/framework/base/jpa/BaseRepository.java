package com.mendao.framework.base.jpa;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * 扩展全局DAO
 * @author zhaolei
 *
 * @param <T>
 * @param <ID>
 */
public interface BaseRepository<T, ID extends Serializable>    
extends PagingAndSortingRepository<T, ID> {   
    /**
     * merge
     * @param obj
     */
	public void merge(T obj);
	/**
	 * HQL执行语句返回HQL语句对象集合
	 * @param hql
	 * @return
	 */
	public List<T> findListByHql(String hql);
	/**
	 * 执行SQL语句返回指定对象集合
	 * @param entityClass
	 * @param sql
	 * @return
	 */
	public List<T> findAllBySql(Class<T> entityClass, String sql) ;
	
	public PageEntity<T> findByPage(PageEntity<T> myPage) ;
	
	public int executeQuery(String hql, Map<String, Object> queryParams);
	PageEntity<T> findByPageUsingProcedure(PageEntity<T> myPage, List<Object> params);
	PageEntity<T> findByPageBySql(PageEntity<T> myPage, List<Object> params);
	Object getSingleResult(String hql, List<Object> params);
}  