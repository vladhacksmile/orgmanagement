package com.vladhacksmile.orgservice.integration;

import com.vladhacksmile.orgservice.config.AppConfig;
import com.vladhacksmile.orgservice.model.entity.Employee;
import com.vladhacksmile.orgservice.model.entity.OrganizationDTO;
import com.vladhacksmile.orgservice.model.result.Result;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

@Stateless
public class OrganizationClient {
    private Client client;
    private final String serviceUrl = "http://localhost:8080";

    public static SSLContext createSSLContext(String truststorePath, String truststorePassword) throws Exception {
        KeyStore truststore = KeyStore.getInstance("PKCS12");

        try (InputStream truststoreInput = new FileInputStream(truststorePath)) {
            truststore.load(truststoreInput, truststorePassword.toCharArray());
        }

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(truststore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        return sslContext;
    }

    public static Client createSSLClient() throws Exception {
        SSLContext sslContext = createSSLContext(
                AppConfig.getProperty("truststore.path"),
                AppConfig.getProperty("truststore.key")
        );

        return ClientBuilder.newBuilder()
                .sslContext(sslContext)
                .build();
    }

    public Result<OrganizationDTO> getOrganizationById(long id) {
        String url = serviceUrl + "/organizations/" + id;
        try {
            try {
                client = createSSLClient();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Response response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE).get();

            Result<OrganizationDTO> organizationResult = response.readEntity(new GenericType<>() {});

            client.close();

            return organizationResult;
        } catch (ProcessingException e) {
            return null;
        }
    }

    public Result<Employee> addEmployee(Employee employee) {
        String url = serviceUrl + "/employees";
        try {
            try {
                client = createSSLClient();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

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
            try {
                client = createSSLClient();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Response response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE).post(null);

            Result<Integer> migrateResult = response.readEntity(new GenericType<>() {});

            client.close();

            return migrateResult;
        } catch (ProcessingException e) {
            return null;
        }
    }

    public Result<OrganizationDTO> deleteOrganizationById(long id) {
        String url = serviceUrl + "/organizations/" + id;
        try {
            try {
                client = createSSLClient();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Response response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE).delete();

            Result<OrganizationDTO> organizationResult = response.readEntity(new GenericType<>() {});

            client.close();

            return organizationResult;
        } catch (ProcessingException e) {
            return null;
        }
    }

}
