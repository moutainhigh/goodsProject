package com.mendao.framework.base.jpa;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public class CustomRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements BaseRepository<T, ID> {

	private final EntityManager entityManager;
	private final Class<T> entityClass;
	private final String entityName;

	public CustomRepository(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getMetadata(domainClass, em), em);
	}

	public CustomRepository(final JpaEntityInformation<T, ?> entityInformation, final EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
		this.entityClass = entityInformation.getJavaType();
		this.entityName = entityInformation.getEntityName();
	}

	/**
	 * merge
	 * 
	 * @param obj
	 */
	@Transactional
	public void merge(T obj) {
		this.entityManager.merge(obj);
	}

	/**
	 * HQL执行语句返回HQL语句对象集合
	 * 
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findListByHql(String hql) {
		Query query = this.entityManager.createQuery(hql);
		List<T> result = query.getResultList();
		return result;
	}

	/**
	 * 执行SQL语句返回指定对象集合
	 * 
	 * @param entityClass
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAllBySql(Class<T> entityClass, String sql) {
		// 创建原生SQL查询QUERY实例,指定了返回的实体类型
		Query query = entityManager.createNativeQuery(sql, entityClass);
		// 执行查询，返回的是实体列表,
		List<T> EntityList = query.getResultList();
		return EntityList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageEntity<T> findByPage(PageEntity<T> myPage) {
		myPage.setFirstRange((myPage.getCurrentpage() - 1) * myPage.getPagesize());
		String whereHql = buildWhereQuery(myPage.getParams());
		String orderHql = myPage.getOrderBy();
		String groupHql = myPage.getGroupBy();
		String hql = "select count(*) from " + entityName + " o ";
		Query query = createQuery(hql + whereHql  + groupHql + orderHql, myPage.getParams());
		if(groupHql != null && !groupHql.equals("")){
			myPage.setTotalpage(query.getResultList().size() / myPage.getPagesize() + 1);
		}else{
			myPage.setTotalpage(((Long) query.getSingleResult()).intValue() / myPage.getPagesize() + 1);
		}
		hql = "select o from " + entityName + " o ";
		query = createQuery(hql + whereHql + groupHql + orderHql, myPage.getParams());
		query.setFirstResult(myPage.getFirstRange()).setMaxResults(myPage.getPagesize());
		myPage.setResult(query.getResultList());
		return myPage;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageEntity<T> findByPageUsingProcedure(PageEntity<T> myPage, List<Object> params) {
		myPage.setFirstRange((myPage.getCurrentpage() - 1) * myPage.getPagesize());
		
		Query query = entityManager.createNativeQuery(myPage.getProcedure(), entityClass);
		if(null != params && !params.isEmpty()){
			for(int i=0; i<params.size(); i++){
				query.setParameter(i+1, params.get(i));
			}
		}
		
		//query.setFirstResult(myPage.getFirstRange()).setMaxResults(myPage.getPagesize());
		List<T> result = query.getResultList();
		myPage.setResult(result);
		return myPage;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageEntity<T> findByPageBySql(PageEntity<T> myPage, List<Object> params) {
		myPage.setFirstRange((myPage.getCurrentpage() - 1) * myPage.getPagesize());
		
		Query query = entityManager.createNativeQuery(myPage.getProcedure(), entityClass);
		if(null != params && !params.isEmpty()){
			for(int i=0; i<params.size(); i++){
				query.setParameter(i+1, params.get(i));
			}
		}
		
		query.setFirstResult(myPage.getFirstRange()).setMaxResults(myPage.getPagesize());
		List<T> result = query.getResultList();
		myPage.setResult(result);
		return myPage;
	}
	
	@Override
	public Object getSingleResult(String hql, List<Object> params){
		Query query = entityManager.createNativeQuery(hql);
		if(null != params && !params.isEmpty()){
			for(int i=0; i<params.size(); i++){
				query.setParameter(i+1, params.get(i));
			}
		}
		return query.getSingleResult();
	}

	@SuppressWarnings("rawtypes")
	private void setQueryParams(Query query, Map<String, Object> queryParams) {
		if (queryParams != null && queryParams.size() > 0) {
			for (String key : queryParams.keySet()) {
				Class clazz = getFieldType(this.entityClass, key);
				String key0 = key.replace(".", "");
				if (clazz != null && clazz.equals(String.class) && !key.endsWith("_in")) {
					query.setParameter(key0, '%' + queryParams.get(key).toString() + '%');
				} else if (clazz != null && (clazz.equals(Integer.class) || clazz.equals(int.class))) {
					query.setParameter(key0, Integer.valueOf(queryParams.get(key).toString()));
				} else if (clazz != null && (clazz.equals(Long.class) || clazz.equals(long.class))) {
					query.setParameter(key0, Long.valueOf(queryParams.get(key).toString()));
				} else if (clazz != null && clazz.equals(Boolean.class)) {
					query.setParameter(key0, Boolean.valueOf(queryParams.get(key).toString()));
				} else {
					query.setParameter(key0, queryParams.get(key));
				}
			}
		}
	}


	@SuppressWarnings("unchecked")
	private String buildWhereQuery(Map<String, Object> queryParams) {
		StringBuffer whereQueryHql = new StringBuffer("");
		if (queryParams != null && queryParams.size() > 0) {
			for (String key : queryParams.keySet()) {
				String key0 = key.replace(".", "");
				if (key.endsWith("_s")) {
					whereQueryHql.append(" and ").append("o.").append(key.replace("_s", "")).append(" >=:").append(key0);
				} else if (key.endsWith("_e")) {
					whereQueryHql.append(" and ").append("o.").append(key.replace("_e", "")).append(" <=:").append(key0);
				} else if (key.endsWith("_in")) {
					whereQueryHql.append(" and ").append("o.").append(key.replace("_in", "")).append(" in:").append(key0);
				} else if (key.endsWith("_notin")) {
					whereQueryHql.append(" and ").append("o.").append(key.replace("_notin", "")).append(" not in:").append(key0);
				} else {
					Class<T> clazz = getFieldType(this.entityClass, key);
					if (clazz != null && clazz.equals(String.class)) {
						whereQueryHql.append(" and ").append("o.").append(key).append(" like :").append(key0);
					} else {
						whereQueryHql.append(" and ").append("o.").append(key).append(" =:").append(key0);
					}

				}
			}
		}

		return whereQueryHql.toString().replaceFirst(" and ", " where ");
	}

	private Query createQuery(String hql, Map<String, Object> queryParams) {
		Query query = entityManager.createQuery(hql);
		setQueryParams(query, queryParams);
		return query;
	}

	@SuppressWarnings("rawtypes")
	private Class getFieldType(Class clazz, String fieldName) {
		String[] arr = fieldName.split("\\.");
//		List<String> fields = Arrays.asList(arr);
//		for(String s : fields){
//			System.out.println(s);
//		}
		return getFieldType(clazz, arr);
	}
	
	private Class getFieldType(Class clazz, String[] fields){
		Class typeClass = null;
		try {
			typeClass = clazz.getDeclaredField(fields[0]).getType();
			if(fields.length > 1){
				String[] newArr = Arrays.copyOfRange(fields, 1, fields.length);
				return getFieldType(typeClass, newArr);
			}
			
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
		
		return typeClass;
	}

	@Override
	public int executeQuery(String hql, Map<String, Object> queryParams) {
		Query query = createQuery(hql, queryParams);
		return query.executeUpdate();
	}
}