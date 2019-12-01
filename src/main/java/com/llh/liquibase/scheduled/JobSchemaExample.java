/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.scheduled;

import com.llh.liquibase.annotation.ChangeSchema;
import com.llh.liquibase.domain.Client;
import com.llh.liquibase.service.ClientService;
import com.llh.liquibase.service.SchemaDynamicService;
import com.llh.liquibase.util.DatabaseContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 *
 * @author lorenzolince
 */
@Component
public class JobSchemaExample {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String schema1 = "EXAMPLE_SCHMA_1";
    private static String schema2 = "EXAMPLE_SCHMA_2";
    @Autowired
    private ClientService clientService;

    @Autowired
    private SchemaDynamicService schemaDynamicService;
    @Autowired
    AutowireCapableBeanFactory beanFactory;

    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    interface myInterface {

        @ChangeSchema
        void save(String schema, Client client);
    }

    @Scheduled(initialDelay = 120000, fixedDelay = 300000)
    public void testJob() {
        String job = System.getProperty("job");

        logger.info("################## testJob ##################");

        if (job == null) {
            logger.info("################## crete schema ##################");
            System.setProperty("job", "job");
            schemaDynamicService.createNewSchema(schema1);
            schemaDynamicService.createNewSchema(schema2);
            Client client = new Client();
            client.setName("Test Job ");
            client.setSchema(schema1);
            DatabaseContextHolder.set(client.getSchema());
            save(client);
            client = new Client();
            client.setName("Test Job ");
            client.setSchema(schema2);
            DatabaseContextHolder.set(client.getSchema());
            save(client);
            logger.info("################## crete schema ##################");
            logger.info("view in --> http://localhost:8088/liquibase/api/client/all?schemaName=EXAMPLE_SCHMA_1");
            logger.info("view in --> http://localhost:8088/liquibase/api/client/all?schemaName=EXAMPLE_SCHMA_2");
        } else {
            try {
                logger.info("################## drop example schemas ##################");
                schemaDynamicService.dropSchema(schema1);
                schemaDynamicService.dropSchema(schema2);

            } finally {
                logger.info("################## destroy Scheduled ##################");
                postProcessor.postProcessBeforeDestruction(this, "jobSchemaExample");
                postProcessor.destroy();
                logger.info("################## destroyed ##################");

            }

        }

    }

    @ChangeSchema
    public void save(Client client) {
        clientService.saveClient(client);
    }

}
