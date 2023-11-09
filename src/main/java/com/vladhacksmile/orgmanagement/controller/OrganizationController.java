package com.vladhacksmile.orgmanagement.controller;

import com.vladhacksmile.orgmanagement.dto.OrganizationDTO;
import com.vladhacksmile.orgmanagement.dto.SearchDTO;
import com.vladhacksmile.orgmanagement.model.entity.Organization;
import com.vladhacksmile.orgmanagement.model.result.Result;
import com.vladhacksmile.orgmanagement.model.result.SearchResult;
import com.vladhacksmile.orgmanagement.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    @GetMapping
    public SearchResult<Organization> getAll(SearchDTO searchDTO) {
        return organizationService.getAll(searchDTO);
    }

    @GetMapping("/{id}")
    public Result<Organization> get(@PathVariable Long id) {
        return organizationService.get(id);
    }

    @PostMapping
    public Result<Organization> add(@RequestBody OrganizationDTO organizationDTO) {
        return organizationService.add(organizationDTO);
    }

    @PutMapping
    public Result<Organization> put(OrganizationDTO organizationDTO) {
        return organizationService.put(organizationDTO);
    }

    @DeleteMapping("/{id}")
    public Result<Organization> delete(@PathVariable Long id) {
        return organizationService.delete(id);
    }
}
