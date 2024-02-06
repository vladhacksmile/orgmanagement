package com.vladhacksmile.orgmanagement.controller;

import dto.EmployeeDTO;
import lombok.extern.slf4j.Slf4j;
import model.ResponseMapper;
import model.entity.Employee;
import model.result.Result;
import model.result.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.EmployeeService;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Result<SearchResult<Employee>>> getAll(@RequestParam(name = "page_num", defaultValue = "1") int pageNum,
                                                                 @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        return ResponseMapper.map(employeeService.getAll(pageNum, pageSize));
    }

    @GetMapping("/organization/{id}")
    public ResponseEntity<Result<SearchResult<Employee>>> getAllByOrganization(@RequestParam(name = "page_num", defaultValue = "1") int pageNum,
                                                                 @RequestParam(name = "page_size", defaultValue = "10") int pageSize,
                                                                               @PathVariable Long id) {
        return ResponseMapper.map(employeeService.getAllByOrganization(pageNum, pageSize, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<EmployeeDTO>> get(@PathVariable Long id) {
        return ResponseMapper.map(employeeService.get(id));
    }

    @PostMapping
    public ResponseEntity<Result<EmployeeDTO>> add(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseMapper.map(employeeService.add(employeeDTO));
    }

    @PutMapping
    public ResponseEntity<Result<EmployeeDTO>> put(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseMapper.map(employeeService.put(employeeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<EmployeeDTO>> delete(@PathVariable Long id) {
        return ResponseMapper.map(employeeService.delete(id));
    }

    @PostMapping("/migrate/{organizationId1}/{organizationId2}")
    public ResponseEntity<Result<Integer>> migrateEmployees(@PathVariable Long organizationId1, @PathVariable Long organizationId2) {
        return ResponseMapper.map(employeeService.migrateEmployees(organizationId1, organizationId2));
    }
}
