/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.aspect;

import com.llh.liquibase.annotation.ChangeSchema;
import com.llh.liquibase.util.DatabaseContextHolder;
import com.llh.liquibase.util.DatabaseSessionManager;
import com.llh.liquibase.util.HttpSchemaUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author lorenzolince
 */
@Aspect
@Component
public class ChangeSchemaAcpect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DatabaseSessionManager databaseSessionManager;

    @Around("@annotation(changeSchema)")
    public synchronized Object proceed(ProceedingJoinPoint pjp, ChangeSchema changeSchema) throws Throwable {
        String schema = null;
        try {
            schema = HttpSchemaUtil.getSchema();
            if (schema == null) {
                schema = (String) pjp.getArgs()[0];
            }
            logger.info("schema:  -> " + schema);
            databaseSessionManager.bindSession(schema);
            Object result = pjp.proceed();
            return result;
        } finally {
            logger.info("clear DatabaseContextHolder");
            databaseSessionManager.bindSession();
            DatabaseContextHolder.clear();
        }
    }
}
