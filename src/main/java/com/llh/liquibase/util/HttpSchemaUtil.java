/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author lorenzolince
 */
@Component
public class HttpSchemaUtil  {

    public static void changeSchema(String schema) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            request.setAttribute("schemaName", schema);
        }
    }

    public static String getSchema() {
        String schema = null;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();

            schema = (String) request.getAttribute("schemaName");
            if (schema == null) {
                schema = request.getParameter("schemaName");
            }
        }
        return schema;
    }

}
