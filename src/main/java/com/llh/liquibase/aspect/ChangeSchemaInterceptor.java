/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.aspect;

import com.llh.liquibase.annotation.ChangeSchema;
import  com.llh.liquibase.util.DatabaseContextHolder;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author lorenzolince
 */
@Aspect
@Component
public class ChangeSchemaInterceptor {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(changeSchema)")
    public synchronized Object proceed(ProceedingJoinPoint pjp, ChangeSchema changeSchema) throws Throwable {
        String schema = null;
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                logger.info("#####  requestAttributes #######");
                HttpServletRequest request = requestAttributes.getRequest();

                schema = (String) request.getAttribute("schemaName");
                if (schema == null) {
                    schema = request.getParameter("schemaName");
                }
            } else {
                logger.info("#####  getArgs #######");
                schema = (String) pjp.getArgs()[0];
            }
            logger.info("schema:  -> " + schema);
            DatabaseContextHolder.set(schema);
            Object result = pjp.proceed();
            DatabaseContextHolder.clear();
            return result;
        } finally {
            logger.info("clear DatabaseContextHolder");
            DatabaseContextHolder.clear();
        }
    }   
}
