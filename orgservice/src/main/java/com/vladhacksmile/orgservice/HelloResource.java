package com.vladhacksmile.orgservice;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "hello";
    }

    @GET
    @Path("/hire")
    @Produces("text/plain")
    public String hire() {

        return "робит";
    }
}