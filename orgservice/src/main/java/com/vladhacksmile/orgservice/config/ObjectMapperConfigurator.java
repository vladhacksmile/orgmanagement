package com.vladhacksmile.orgservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObjectMapperConfigurator {
    @Produces(MediaType.APPLICATION_JSON)
    public ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }
}
