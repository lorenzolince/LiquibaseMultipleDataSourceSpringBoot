/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.controller;

import com.llh.liquibase.domain.Client;
import com.llh.liquibase.service.ClientService;
import com.llh.liquibase.util.DatabaseContextHolder;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Client> getAllClient() {
        return clientService.getAllclients();
    }

    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public Client getClientById(@RequestParam(name = "id", required = true) Integer id) {
        return clientService.getClientById(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveClient(@RequestBody Client client) {
        clientService.saveClient(client);
    }

    @RequestMapping(value = "/saveClientSchemas", method = RequestMethod.POST)
    public void saveClientSchemas(@RequestParam(name = "schemaName1", required = true) String schemaName1,
            @RequestParam(name = "schemaName2", required = true) String schemaName2,
            @RequestBody Client client) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            try {
                clientService.saveClient(schemaName1, client);
            } catch (Exception e) {
                System.out.println("Exception:  " + e.getMessage());
            }
        });
        executor.submit(() -> {
            try {
                clientService.saveClient(schemaName2, client);
            } catch (Exception e) {
                System.out.println("Exception:  " + e.getMessage());
            }
        });
        executor.shutdown();
    }
}
