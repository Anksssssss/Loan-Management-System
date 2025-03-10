package com.Ankssss.LoanManagement.repository;

import com.Ankssss.LoanManagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findById(int id);
    Optional<Role> findByName(String name);
}
