/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.conf;

import com.llh.liquibase.util.DynamicDataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author lorenzolince
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.llh.liquibase.repository"},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
@EnableAutoConfiguration
@EnableTransactionManagement
public class ConfigurationApp {

    @Value("${master.datasource.username}")
    private String DEFAULT_DS_USER;
    @Value("${master.datasource.driver-class-name}")
    private String driver;
    @Value("${master.datasource.url}")
    private String url;
    @Value("${master.datasource.password}")
    private String password;
    @Value("${spring.jpa.database-platform}")
    private String dialect;
    @Primary
    @Bean(name = "dataSource", destroyMethod = "")
    public DataSource dataSource() {
        return new DynamicDataSource()
                .setDriver(driver)
                .setUrl(url)
                .setDefaultUser(DEFAULT_DS_USER)
                .setPassword(password);

    }
    @Primary
    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(new String[]{"com.llh.liquibase.domain"});
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public SpringLiquibase liquibase(@Qualifier("dataSource") DataSource dataSource) throws IllegalArgumentException, NamingException {
        SpringLiquibase liquibase = new SpringLiquibase();
        try {
            String defaultSchema = dataSource().getConnection().getSchema();
            System.setProperty("defaultSchemaName", defaultSchema);
            System.setProperty("masterSchemaName", defaultSchema);
            String changelogFile = "classpath:/database/module-database.xml";
            liquibase.setChangeLog(changelogFile);
            liquibase.setDefaultSchema(defaultSchema);
            liquibase.setDataSource(dataSource);
            liquibase.setDropFirst(false);
            liquibase.setContexts(defaultSchema);
            liquibase.setShouldRun(true);
            Map<String, String> params = new HashMap<>();
            params.put("verbose", "true");
            liquibase.setChangeLogParameters(params);
        } catch (Exception e) {
            System.out.println(" Exception: " + e.getMessage());
        }
        return liquibase;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        properties.setProperty("hibernate.dialect", dialect);
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory") final EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
