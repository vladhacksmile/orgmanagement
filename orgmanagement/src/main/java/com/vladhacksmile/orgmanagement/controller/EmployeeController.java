package com.vladhacksmile.orgmanagement.controller;

import com.vladhacksmile.orgmanagement.dto.EmployeeDTO;
import com.vladhacksmile.orgmanagement.model.ResponseMapper;
import com.vladhacksmile.orgmanagement.model.entity.Employee;
import com.vladhacksmile.orgmanagement.model.result.Result;
import com.vladhacksmile.orgmanagement.model.result.SearchResult;
import com.vladhacksmile.orgmanagement.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Result<SearchResult<Employee>>> getAll(@RequestParam(name = "page_num", defaultValue = "1") int pageNum,
                                                                  @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        return ResponseMapper.map(employeeService.getAll(pageNum, pageSize));
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
