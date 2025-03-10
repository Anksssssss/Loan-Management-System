package com.Ankssss.LoanManagement.repository;

import com.Ankssss.LoanManagement.constants.RepaymentStatus;
import com.Ankssss.LoanManagement.entity.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, String> {

    Optional<Repayment> findById(String id);

    List<Repayment> findByLoanId(String loanId);

    List<Repayment> findByLoanIdAndStatus(String loanId, RepaymentStatus status);

    Repayment findFirstByLoanIdAndStatusOrderByDueDateAsc(String loanId, RepaymentStatus status);

    @Query("SELECT r FROM Repayment r WHERE r.loan.id = :loanId AND r.loan.user.id = :userId")
    List<Repayment> findByUserAndLoan(@Param("userId") String userId, @Param("loanId") String loanId);

}
