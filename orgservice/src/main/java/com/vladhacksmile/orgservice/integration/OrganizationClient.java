package com.vladhacksmile.orgservice.integration;

import com.vladhacksmile.orgservice.model.entity.Employee;
import com.vladhacksmile.orgservice.model.entity.Organization;
import com.vladhacksmile.orgservice.model.result.Result;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Stateless
public class OrganizationClient {
    private Client client;
    private final String serviceUrl = "https://localhost:8080/api/v1";

    public Result<Organization> getOrganizationById(long id) {
        String url = serviceUrl + "/organizations/" + id;
        try {
            client = ClientBuilder.newClient();

            Response response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE).get();

            Result<Organization> organizationResult = response.readEntity(new GenericType<>() {});

            client.close();

            return organizationResult;
        } catch (ProcessingException e) {
            return null;
        }
    }

    public Result<Employee> addEmployee(Employee employee) {
        String url = serviceUrl + "/employees/";
        try {
            client = ClientBuilder.newClient();

            Response response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(employee));

            Result<Employee> employeeResult = response.readEntity(new GenericType<>() {});

            client.close();

            return employeeResult;
        } catch (ProcessingException e) {
            return null;
        }
    }

    public Result<Integer> migrateEmployees(Long organizationId1, Long organizationId2) {
        String url = serviceUrl + "/employees/migrate/" + organizationId1 + "/" + organizationId2;
        try {
            client = ClientBuilder.newClient();

            Response response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE).post(null);

            Result<Integer> migrateResult = response.readEntity(new GenericType<>() {});

            client.close();

            return migrateResult;
        } catch (ProcessingException e) {
            return null;
        }
    }

    public Result<Organization> deleteOrganizationById(long id) {
        String url = serviceUrl + "/organizations/" + id;
        try {
            client = ClientBuilder.newClient();

            Response response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE).delete();

            Result<Organization> organizationResult = response.readEntity(new GenericType<>() {});

            client.close();

            return organizationResult;
        } catch (ProcessingException e) {
            return null;
        }
    }

}
