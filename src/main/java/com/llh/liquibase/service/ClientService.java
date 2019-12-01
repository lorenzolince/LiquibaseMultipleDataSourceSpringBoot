/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.service;

import com.llh.liquibase.domain.Client;
import com.llh.liquibase.repository.ClientRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lorenzolince
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    /**
     *
     * @return
     */
    public List<Client> getAllclients() {
        return clientRepository.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public Client getClientById(Integer id) {

        return clientRepository.findOne(id);
    }

    /**
     * for processes no http request, for example, jms listener, thread,
     * scheduled task ... etc.
     *
     * @param schema
     * @param client
     */
    @Transactional
    public void saveClient(String schema, Client client) {
        clientRepository.save(client);
    }

    /**
     *
     * @param client
     */
    @Transactional
    public void saveClient(Client client) {
        clientRepository.save(client);
    }
}
