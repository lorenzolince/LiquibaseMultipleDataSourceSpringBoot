/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.controller;

import com.llh.liquibase.service.SchemaDynamicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lorenzolince
 */
@RestController
@RequestMapping("api//schema")
public class SchemaDynamicRestController {

    @Autowired
    private SchemaDynamicService schemaDynamicService;
    private static final Logger LOGGER
            = LoggerFactory.getLogger(SchemaDynamicRestController.class);

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createSchema(@RequestParam(name = "schemaName", required = true) String schemaName) {
       LOGGER.info("create new schema: "+schemaName);
      schemaDynamicService.createNewSchema(schemaName);
      LOGGER.info("################# FINISH #####################");
    }
 @RequestMapping(value = "/drop", method = RequestMethod.POST)
    public void dropSchema(@RequestParam(name = "schemaName", required = true) String schemaName) {
       LOGGER.info("drop  schema: "+schemaName);
       schemaDynamicService.dropSchema(schemaName);
       LOGGER.info("################# FINISH #####################");
    }
}
