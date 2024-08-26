package com.practice.demo.repository;

import com.practice.demo.entitiy.Role;
import com.practice.demo.entitiy.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
