package com.Ankssss.LoanManagement.aspect;

import com.Ankssss.LoanManagement.DTO.ApproveApplicationDTO;
import com.Ankssss.LoanManagement.entity.Loan;
import com.Ankssss.LoanManagement.entity.LoanApplication;
import com.Ankssss.LoanManagement.entity.Repayment;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Slf4j
public class LoanApprovalLoggingAspect {

    @Pointcut("execution(* com.Ankssss.LoanManagement.service.impl.LoanApplicationServiceImpl.updateStatus(..))")
    public void loanApplicationUpdate() {}

    @Pointcut("execution(* com.Ankssss.LoanManagement.service.impl.LoanServiceImpl.addLoan(..))")
    public void loanCreation() {}

    @Pointcut("execution(* com.Ankssss.LoanManagement.service.impl.RepaymentServiceImpl.createRepaymentSchedule(..))")
    public void repaymentScheduleCreation() {}

    @Around("loanApplicationUpdate()")
    public Object logLoanApplicationUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String applicationId = (String) args[0];
        String status = args[1].toString();

        log.info("Starting Loan Application Update | Application ID: {} | New Status: {}", applicationId, status);
        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("Loan Application Updated Successfully - Application ID: {} | Status: {} | Execution Time: {}ms",
                    applicationId, status, executionTime);
            return result;
        } catch (Exception e) {
            log.error("Error Updating Loan Application - Application ID: {} | Status: {} | Error: {}", applicationId, status, e.getMessage());
            throw e;
        }
    }

    @Around("loanCreation()")
    public Object logLoanCreation(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        ApproveApplicationDTO request = (ApproveApplicationDTO) args[0];
        String applicationId = request.getApplicationId();
        LoanApplication applicationDetails = (LoanApplication) args[1];

        log.info("Starting Loan Creation - Application ID: {} | Interest Rate: {} | Tenure: {} months",
                applicationId, request.getInterestRate(), request.getTenure());
        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            Loan loan = (Loan) result;
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("Loan Created Successfully - Loan ID: {} | Application ID: {} | Amount: {} | Execution Time: {}ms",
                    loan.getId(), applicationId, loan.getAmount(), executionTime);
            return result;
        } catch (Exception e) {
            log.error("Error Creating Loan | Application ID: {} | Error: {}", applicationId, e.getMessage());
            throw e;
        }
    }


    @Around("repaymentScheduleCreation()")
    public Object logRepaymentScheduleCreation(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Loan loan = (Loan) args[0];
        String loanId = loan.getId();

        log.info("Starting Repayment Schedule Creation - Loan ID: {}", loanId);
        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            List<Repayment> repayments = loan.getRepayments();
            int repaymentCount = repayments != null ? repayments.size() : 0;

            log.info("Repayment Schedule Created Successfully - Loan ID: {} | Repayment Count: {} | Execution Time: {}ms",
                    loanId, repaymentCount, executionTime);
            return result;
        } catch (Exception e) {
            log.error("Error Creating Repayment Schedule - Loan ID: {} | Error: {}", loanId, e.getMessage());
            throw e;
        }
    }

}
