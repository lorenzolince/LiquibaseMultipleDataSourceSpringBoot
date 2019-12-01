/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 *
 * @author lorenzolince
 */
@Service
public class DatabaseSessionManager {
  @Autowired
  private EntityManagerFactory entityManagerFactory;
  
   public  void bindSession(String schema) {
        if (!TransactionSynchronizationManager.hasResource(entityManagerFactory)) {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            TransactionSynchronizationManager.bindResource(entityManagerFactory, new EntityManagerHolder(entityManager));
        }
        DatabaseContextHolder.set(schema);
        unbindSession() ;
    }
  
   public  void bindSession() {
        if (!TransactionSynchronizationManager.hasResource(entityManagerFactory)) {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            TransactionSynchronizationManager.bindResource(entityManagerFactory, new EntityManagerHolder(entityManager));
        }
    }

    public  void unbindSession() {
        EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager
            .unbindResource(entityManagerFactory);
        EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager());
    }
}
