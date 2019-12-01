/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.util;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author lorenzolince
 */
@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${master.datasource.username}")
    private String DEFAULT_DS_USER;

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = DatabaseContextHolder.getDatabase();
        if (tenantId == null) {
           tenantId = HttpSchemaUtil.getSchema();
           DatabaseContextHolder.set(tenantId);
         }
        logger.info("schema:  -> " + tenantId);
        if (tenantId != null) {
            return tenantId;
        }
        return DEFAULT_DS_USER;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
