/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.util;

import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *
 * @author lorenzolince
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private String driver;
    private String defaultUser;
    private String url;
    private String password;
    private DataSource dataSource;

    public DynamicDataSource() {
        setTargetDataSources(new HashMap<>());
    }

    @Override
    protected DataSource determineTargetDataSource() {
        dataSource = getDatsource(determineCurrentLookupKey());
        return dataSource;
    }

    @Override
    protected String determineCurrentLookupKey() {
        if (DatabaseContextHolder.getDatabase() != null) {

            String schema = DatabaseContextHolder.getDatabase();

            if (schema != null) {
                return schema;
            } else {
                return defaultUser;
            }
        } else {
            return defaultUser;
        }

    }

    private DataSource getDatsource(String userSchema) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.driver);
        dataSource.setUrl(this.url);
        dataSource.setUsername(userSchema);
        dataSource.setPassword(this.password);
        return dataSource;
    }

    public DynamicDataSource setDefaultUser(String defaultUser) {
        this.defaultUser = defaultUser;
        return this;
    }

    public DynamicDataSource setDriver(String driver) {
        this.driver = driver;
        return this;
    }

    public DynamicDataSource setUrl(String url) {
        this.url = url;
        return this;
    }

    public DynamicDataSource setPassword(String password) {
        this.password = password;
        return this;
    }
}
