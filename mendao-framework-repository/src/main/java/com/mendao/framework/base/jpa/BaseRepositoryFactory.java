package com.mendao.framework.base.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.util.Assert;  
  
  
/** 
 *  
 * 
 */

public class BaseRepositoryFactory extends JpaRepositoryFactory {  
	
    private final EntityManager entityManager;  
      
    public BaseRepositoryFactory(EntityManager entityManager) {  
        super(entityManager);  
        Assert.notNull(entityManager);  
        this.entityManager = entityManager;  
          
    }  
      
    @Override  
    protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata, EntityManager entityManager) {  
  
        //TODO  
        JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());  
        return new CustomRepository(entityInformation, entityManager); // custom implementation  
    }  
    
    @Override  
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {  
   
        return CustomRepository.class;  
    }  
}  
