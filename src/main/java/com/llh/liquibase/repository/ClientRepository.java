/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.repository;

import com.llh.liquibase.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lorenzolince
 */
@Repository
@RestResource(exported = false)
public interface ClientRepository  extends JpaRepository<Client, Integer>{
    
}
