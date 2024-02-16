package com.vladhacksmile.orgservice.integration;

import com.vladhacksmile.orgservice.model.entity.Employee;
import com.vladhacksmile.orgservice.model.entity.OrganizationDTO;
import com.vladhacksmile.orgservice.model.result.Result;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;

@Service
public class OrganizationClient {

    private final String serviceUrl = "http://localhost:8085/api/v1";

    @Autowired
    private RestTemplate restTemplate;

    @SneakyThrows
    public Result<Employee> addEmployee(Employee employee) {
        String url = serviceUrl + "/employees";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(employee, headers),
                new ParameterizedTypeReference<Result<Employee>>(){}).getBody();
    }

    @SneakyThrows
    public Result<Integer> migrateEmployees(Long organizationId1, Long organizationId2) {
        String url = serviceUrl + "/employees/migrate/" + organizationId1 + "/" + organizationId2;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<Result<Integer>>(){}).getBody();
    }

    @SneakyThrows
    public Result<OrganizationDTO> deleteOrganizationById(long id) {
        String url = serviceUrl + "/organizations/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_XML);
        return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<Result<OrganizationDTO>>(){}).getBody();
    }

}
