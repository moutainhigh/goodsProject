package com.mendao.framework.base.jpa;


import java.io.Serializable;   
  

import javax.persistence.EntityManager;   
  
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;   
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;   
import org.springframework.data.repository.core.support.RepositoryFactorySupport;   

public class BaseRepositoryFactoryBean<T extends JpaRepository<Object, Serializable>> extends JpaRepositoryFactoryBean<T, Object, Serializable> {   
  
    @Override  
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {   
    	
        return new BaseRepositoryFactory(em);   
    }   
}  