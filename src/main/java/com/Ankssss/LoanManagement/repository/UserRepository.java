package com.Ankssss.LoanManagement.repository;

import com.Ankssss.LoanManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    public User findByEmail(String email);
    public Optional<User> findById(String userId);

    List<User> findByRoleName(String roleName);
}
