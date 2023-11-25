package com.vladhacksmile.orgmanagement.controller;

import com.vladhacksmile.orgmanagement.dto.OrganizationDTO;
import com.vladhacksmile.orgmanagement.model.ResponseMapper;
import com.vladhacksmile.orgmanagement.model.entity.Organization;
import com.vladhacksmile.orgmanagement.model.result.Result;
import com.vladhacksmile.orgmanagement.model.result.SearchResult;
import com.vladhacksmile.orgmanagement.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<Result<SearchResult<Organization>>> getAll(
            @RequestParam(name = "page_num", defaultValue = "1") int pageNum,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort_type", defaultValue = "ASC") String sortType,
            @RequestParam(value = "sort_column", defaultValue = "") String sortColumn,
            @RequestParam(value = "filter_operation", defaultValue = "") String filterOperation,
            @RequestParam(value = "filter_field", defaultValue = "") String filterField,
            @RequestParam(value = "filter_value", defaultValue = "") String filterValue) {
        return ResponseMapper.map(organizationService.getAll(pageNum, pageSize, sortType, sortColumn,
                filterOperation, filterField, filterValue));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<Organization>> get(@PathVariable Long id) {
        return ResponseMapper.map(organizationService.get(id));
    }

    @PostMapping
    public ResponseEntity<Result<Organization>> add(@RequestBody OrganizationDTO organizationDTO) {
        return ResponseMapper.map(organizationService.add(organizationDTO));
    }

    @PutMapping
    public ResponseEntity<Result<Organization>> put(@RequestBody OrganizationDTO organizationDTO) {
        return ResponseMapper.map(organizationService.put(organizationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Organization>> delete(@PathVariable Long id) {
        return ResponseMapper.map(organizationService.delete(id));
    }
}
