/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author lorenzolince
 */
@Entity
@Table(name = "CLIENT")
@SequenceGenerator(name = "SequenceClient",
        sequenceName = "S_CLIENT", allocationSize = 1)
public class Client  implements Serializable{
    
    private static final long serialVersionUID = 3716143772023645465L;

    /**
     *
     */
    @Id
    @Column(name = "CODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "SequenceClient")
    private Integer code; 
    
     /**
     *
     */
    @Column(name = "NAME")
    private String name;
    
     /**
     *
     */
    @Column(name = "schema", updatable = false)
    private String schema;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
    
    
    
    
}
