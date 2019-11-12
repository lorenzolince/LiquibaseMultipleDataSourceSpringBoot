/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.util;

import javax.naming.NamingException;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author lorenzolince
 */
@Component
public class RunLiquibase {

    @Autowired
    AutowireCapableBeanFactory beanFactory;

    @Autowired
    private SpringLiquibase liquibase;
    @Autowired
    private DataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(RunLiquibase.class);

    public void updateControllChange(String schemaName) {
        try {
            System.setProperty("defaultSchemaName", schemaName);
            beanFactory.autowireBean(liquibase(schemaName));
            beanFactory.initializeBean(liquibase, "liquibase");
        } catch (Exception ex) {
           LOGGER.error(ex.getMessage());
        }
    }

    private SpringLiquibase liquibase(String defaultSchema) throws IllegalArgumentException, NamingException {

        try {
            liquibase.setDefaultSchema(defaultSchema);
            liquibase.setDataSource(dataSource);
            liquibase.setContexts(defaultSchema);
            liquibase.setShouldRun(true);
        } catch (Exception e) {
             LOGGER.error(e.getMessage());
        }
        return liquibase;
    }
}
