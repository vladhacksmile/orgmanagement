package com.vladhacksmile.orgservice.manager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/orgmanager")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationManagerController {
    @POST
    @Path("/hire/{id}")
    public Response getFlatWithBalcony(@PathParam("id") Long id) {

        return Response.ok()
                .entity(null)
                .build();

//        throw new IllegalArgumentException("Invalid parameters supplied");
    }
}
