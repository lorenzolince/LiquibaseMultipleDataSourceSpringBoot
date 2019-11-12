/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.controller;

import com.llh.liquibase.domain.Client;
import com.llh.liquibase.repository.ClientRepository;
import com.llh.liquibase.util.DatabaseContextHolder;
import java.util.List;
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
    private ClientRepository clientRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Client> getAllClient(@RequestParam(name = "schemaName", required = true) String schemaName) {
        DatabaseContextHolder.set(schemaName);
        List<Client> clients = clientRepository.findAll();
        DatabaseContextHolder.clear();
        return clients;
    }
    
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public Client getClientById(@RequestParam(name = "schemaName", required = true) String schemaName,@RequestParam(name = "id", required = true) Integer id) {
        DatabaseContextHolder.set(schemaName);
        Client client = clientRepository.findOne(id);
        DatabaseContextHolder.clear();
        return client;
    }
    
     @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveClient(@RequestParam(name = "schemaName", required = true) String schemaName,
                           @RequestBody Client client) {
        DatabaseContextHolder.set(schemaName);
        clientRepository.save(client);
        DatabaseContextHolder.clear();
    }
}
