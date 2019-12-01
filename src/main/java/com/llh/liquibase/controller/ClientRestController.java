/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.controller;

import com.llh.liquibase.annotation.ChangeSchema;
import com.llh.liquibase.domain.Client;
import com.llh.liquibase.service.ClientService;
import com.llh.liquibase.util.DatabaseContextHolder;
import com.llh.liquibase.util.DatabaseSessionManager;
import com.llh.liquibase.util.HttpSchemaUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lorenzolince
 */
@RestController
@RequestMapping("/api/client")
public class ClientRestController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private TestMultiten testMultiten;
    @Autowired
    private DatabaseSessionManager databaseSessionManager;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "schemaName",
                value = "change dynamic schema", required = true,
                dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Client> getAllClient() {
        return clientService.getAllclients();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "schemaName",
                value = "change dynamic schema", required = true,
                dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public Client getClientById(@RequestParam(name = "id", required = true) Integer id) {
        return clientService.getClientById(id);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "schemaName",
                value = "change dynamic schema", required = true,
                dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveClient(@RequestBody Client client) {
        clientService.saveClient(client);
    }

    @RequestMapping(value = "/saveClientSchemasAspect", method = RequestMethod.POST)
    public void saveClientSchemasAspect(
            @RequestParam(name = "schemaName1", required = true) String schemaName1,
            @RequestParam(name = "schemaName2", required = true) String schemaName2,
            @RequestBody Client client) {
        testMultiten.save(schemaName1, client);
        testMultiten.save(schemaName2, client);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "schemaName",
                value = "change dynamic schema", required = true,
                dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/saveClientSchemasParameter", method = RequestMethod.POST)
    public void saveClientSchemasParameter(
            @RequestParam(name = "schemaName2", required = true) String schemaName2,
            @RequestBody Client client) {
        clientService.saveClient(client);
        HttpSchemaUtil.changeSchema(schemaName2);
        testMultiten.save(client);
    }

    @RequestMapping(value = "/saveClientSchemasNoAspect", method = RequestMethod.POST)
    public void saveClientSchemasNoAspect(
            @RequestParam(name = "schemaName1", required = true) String schemaName1,
            @RequestParam(name = "schemaName2", required = true) String schemaName2,
            @RequestBody Client client) {
        databaseSessionManager.bindSession(schemaName1);
        clientService.saveClient(client);
        databaseSessionManager.bindSession(schemaName2);
        clientService.saveClient(client);
        databaseSessionManager.bindSession();
    }

    @RequestMapping(value = "/saveClientSchemas", method = RequestMethod.POST)
    public void saveClientSchemas(
            @RequestParam(name = "schemaName1", required = true) String schemaName1,
            @RequestParam(name = "schemaName2", required = true) String schemaName2,
            @RequestBody Client client) {
        HttpSchemaUtil.changeSchema(schemaName1);
        testMultiten.save(client);
        HttpSchemaUtil.changeSchema(schemaName2);
        testMultiten.save(client);
    }

    @RequestMapping(value = "/saveClientSchemasThread", method = RequestMethod.POST)
    public void saveClientSchemasThread(
            @RequestParam(name = "schemaName1", required = true) String schemaName1,
            @RequestParam(name = "schemaName2", required = true) String schemaName2,
            @RequestBody Client client) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            try {
                DatabaseContextHolder.set(schemaName1);
                clientService.saveClient(client);
            } catch (Exception e) {
                System.out.println("Exception:  " + e.getMessage());
            }
        });
        executor.submit(() -> {
            try {
                DatabaseContextHolder.set(schemaName2);
                clientService.saveClient(client);
            } catch (Exception e) {
                System.out.println("Exception:  " + e.getMessage());
            }
        });
        executor.shutdown();

    }

    @Component
    public class TestMultiten {

        @ChangeSchema
        public void save(String schema, Client client) {
            clientService.saveClient(schema, client);
        }

        @ChangeSchema
        public void save(Client client) {
            clientService.saveClient(client);
        }
    }
}
