package com.practice.demo.controller;

import com.practice.demo.entitiy.Employee;
import com.practice.demo.request.CreateRequest;
import com.practice.demo.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@EnableMethodSecurity
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER1')")
    public Employee createEmployee(@RequestBody CreateRequest request) {
        return employeeService.createEmployee(request);
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() { return employeeService.getAllEmployees(); }

    @GetMapping("/current/all")
    //@PreAuthorize("hasRole('USER1')")
    public List<Employee> getAllCurrentEmployees() {
        return employeeService.getEmployeesForCurrentUser();
    }

    @GetMapping("/get/{employeeId}")
    @PreAuthorize("hasRole('USER2')")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/update/{employeeId}")
    @PreAuthorize("hasRole('USER2')")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long employeeId, @RequestBody CreateRequest request) {
        Employee employee = employeeService.updateEmployee(employeeId, request);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/delete/{employeeId}")
    @PreAuthorize("hasRole('USER2')")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
}
