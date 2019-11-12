/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.util;


/**
 *
 * @author lorenzolince
 */
public class DatabaseContextHolder {
   private static ThreadLocal<String> CONTEXT
      = new ThreadLocal<>();
 
    public static void set(String database) {
        CONTEXT.set(database);
    }
 
    public static String getDatabase() {
        return CONTEXT.get();
    }
 
    public static void clear() {
        CONTEXT.remove();
    }   
}
