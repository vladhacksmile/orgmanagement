package com.vladhacksmile.orgmanagement.controller;

import dto.OrganizationDTO;
import lombok.extern.slf4j.Slf4j;
import model.ResponseMapper;
import model.entity.Organization;
import model.result.Result;
import model.result.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.OrganizationService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<Result<SearchResult<Organization>>> getAll(
            @RequestParam(name = "page_num", defaultValue = "1") int pageNum,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort_type", defaultValue = "ASC") String sortType,
            @RequestParam(value = "sort_column", defaultValue = "ID") String sortColumn,
            @RequestParam(value = "filter_operation", defaultValue = "") String filterOperation,
            @RequestParam(value = "filter_field", defaultValue = "") String filterField,
            @RequestParam(value = "filter_value", defaultValue = "") String filterValue) {
        return ResponseMapper.map(organizationService.getAll(pageNum, pageSize, sortType, sortColumn,
                filterOperation, filterField, filterValue));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<OrganizationDTO>> get(@PathVariable Long id) {
        return ResponseMapper.map(organizationService.get(id));
    }

    @PostMapping
    public ResponseEntity<Result<OrganizationDTO>> add(@RequestBody OrganizationDTO organizationDTO) {
        return ResponseMapper.map(organizationService.add(organizationDTO));
    }

    @PutMapping
    public ResponseEntity<Result<OrganizationDTO>> put(@RequestBody OrganizationDTO organizationDTO) {
        return ResponseMapper.map(organizationService.put(organizationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<OrganizationDTO>> delete(@PathVariable Long id) {
        return ResponseMapper.map(organizationService.delete(id));
    }

    @PostMapping("/operations/count-lower-annual-turnover")
    public ResponseEntity<Result<Integer>> countLowerAnnualTurnover(@RequestParam("annual-turnover") Float annualTurnover) {
        return ResponseMapper.map(organizationService.countLowerAnnualTurnover(annualTurnover));
    }

    @PostMapping("/operations/unique-annual-turnovers")
    public ResponseEntity<Result<List<Float>>> uniqueLowerAnnualTurnover() {
        return ResponseMapper.map(organizationService.findUniqueAnnualTurnover());
    }

    @PostMapping("/operations/find-substring")
    public ResponseEntity<Result<SearchResult<Organization>>> findSubstring(
            @RequestParam(name = "page_num", defaultValue = "1") int pageNum,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort_type", defaultValue = "ASC") String sortType,
            @RequestParam(value = "sort_column", defaultValue = "ID") String sortColumn,
            @RequestParam(value = "substring", defaultValue = "") String substring) {
        return ResponseMapper.map(organizationService.findSubstring(pageNum, pageSize, sortType, sortColumn, substring));
    }

}
