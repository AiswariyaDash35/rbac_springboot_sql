package com.practice.demo.repository;

import com.practice.demo.entitiy.Employee;
import com.practice.demo.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByUser(User user);
}
