package com.practice.demo.service;

import com.practice.demo.entitiy.Employee;
import com.practice.demo.request.CreateRequest;

import java.util.List;

public interface EmployeeService {

    public Employee createEmployee(CreateRequest request);
    public List<Employee> getAllEmployees();
    public Employee getEmployeeById(Long id);
    public Employee updateEmployee(Long id,CreateRequest request);
    public void deleteEmployee(Long id);
    public List<Employee> getEmployeesForCurrentUser();
}
