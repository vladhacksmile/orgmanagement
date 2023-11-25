package com.vladhacksmile.orgservice.manager;
import com.vladhacksmile.orgservice.model.entity.Employee;
import com.vladhacksmile.orgservice.model.result.Result;
import com.vladhacksmile.orgservice.service.OrganizationService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.inject.Inject;
import javax.naming.NamingException;

@Path("/orgmanager")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationManagerController {

//    @Inject
//    OrganizationService organizationService;

    OrganizationService organizationService = new OrganizationService();

    @POST
    @Path("/hire/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response hire(@PathParam("id") Long organizationId, Employee employee) {
        if (organizationId == null) {
            throw new IllegalArgumentException("ID is null");
        }
        if (employee == null) {
            throw new IllegalArgumentException("Employee is null");
        }

        Result<?> hireResult = organizationService.hire(organizationId, employee);
        if (hireResult.isError()) {
            return Response.status(hireResult.getStatus().getHttpStatus(), hireResult.getDescription())
                    .build();
        }

        return Response.ok().build();
    }

    @POST
    @Path("/acquise/{organizationId1}/{organizationId2}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response acquise(@PathParam("organizationId1") Long organizationId1, @PathParam("organizationId2") Long organizationId2) {
        if (organizationId1 == null) {
            throw new IllegalArgumentException("organization 1 is null");
        }

        if (organizationId2 == null) {
            throw new IllegalArgumentException("organization 2 is null");
        }

        Result<?> acquiseResult = organizationService.acquise(organizationId1, organizationId2);
        if (acquiseResult.isError()) {
            return Response.status(acquiseResult.getStatus().getHttpStatus(), acquiseResult.getDescription()).build();
        }

        return Response.ok().build();
    }
}
