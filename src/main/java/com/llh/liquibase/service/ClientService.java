/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.service;

import com.llh.liquibase.annotation.ChangeSchema;
import com.llh.liquibase.domain.Client;
import com.llh.liquibase.repository.ClientRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lorenzo
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    /**
     * 
     * @return 
     */
    @ChangeSchema
    public List<Client> getAllclients() {
        return clientRepository.findAll();
    }
    /**
     * 
     * @param id
     * @return 
     */
    @ChangeSchema
    public Client getClientById(Integer id) {

        return clientRepository.findOne(id);
    }
    /**
     * for processes no http request, for example, jms listener, thread, scheduled task ... etc.
     * @param schema
     * @param client 
     */
    @ChangeSchema
    public void saveClient(String schema, Client client) {
        clientRepository.save(client);
    }
    /**
     * 
     * @param client 
     */
    @ChangeSchema
    public void saveClient(Client client) {
        clientRepository.save(client);
    }
}
