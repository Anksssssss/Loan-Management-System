package com.Ankssss.LoanManagement.repository;

import com.Ankssss.LoanManagement.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
