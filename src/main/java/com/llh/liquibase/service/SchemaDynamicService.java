/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.service;

import com.llh.liquibase.util.DatabaseContextHolder;
import com.llh.liquibase.util.RunLiquibase;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lorenzolince
 */
@Service
public class SchemaDynamicService {
    
     @Value("${master.datasource.password}")
    private String password;
     
    public static final String createUser = "CREATE USER #SCHEMA IDENTIFIED BY #USER DEFAULT TABLESPACE USERS TEMPORARY TABLESPACE TEMP;\n"
            + "GRANT CREATE USER, DROP USER, CONNECT, RESOURCE, ALTER USER, CREATE TABLE,CREATE SNAPSHOT, CREATE ANY VIEW, CREATE SESSION TO  #SCHEMA;\n"
            + "ALTER USER #SCHEMA QUOTA UNLIMITED ON USERS\n";
    public static final String dropSchema = "DROP USER #SCHEMA CASCADE";
    @Autowired
    private EntityManager entityManagerFactory;

    @Autowired
    RunLiquibase runLiquibase;

    @Transactional
    public void createNewSchema(String schemaName) {
        String[] commands = createUser.split(";");
        for (String command : commands) {
            String sqlComand = command.replaceAll("#SCHEMA", schemaName);
            Query q = this.entityManagerFactory.createNativeQuery(sqlComand.replaceAll("#USER", password));
            q.executeUpdate();
        }
        runLiquibase.updateControllChange(schemaName);
    }

    @Transactional
    public void dropSchema(String schemaName) {
        String sqlComand = dropSchema.replaceAll("#SCHEMA", schemaName);
        Query q = this.entityManagerFactory.createNativeQuery(sqlComand);
        q.executeUpdate();
        DatabaseContextHolder.clear();
    }
}
