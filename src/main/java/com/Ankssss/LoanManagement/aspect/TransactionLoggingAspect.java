package com.Ankssss.LoanManagement.aspect;

import com.Ankssss.LoanManagement.entity.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TransactionLoggingAspect {

    @Pointcut("execution(* com.Ankssss.LoanManagement.service.impl.TransactionServiceImpl.saveTransaction(..))")
    public void transactionCreation() {}

    @Around("transactionCreation()")
    public Object logTransactionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            log.info("Transaction completed in {} ms", duration);
            return result;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            log.error("Transaction failed in {} ms due to {}", duration, e.getMessage());
            throw e;
        }
    }

    @AfterReturning(value = "transactionCreation()", returning = "transaction")
    public void logTransaction(Transaction transaction) {
        log.info("Transaction Created: \nLoan ID: {} \nUser ID: {} \nRepayment ID: {} \nAmount: {} \nTransaction ID: {}",
                transaction.getLoan().getId(),
                transaction.getUser().getId(),
                transaction.getRepayment().getId(),
                transaction.getAmount(),
                transaction.getId()
        );
    }

    @AfterThrowing(value = "transactionCreation()", throwing = "exception")
    public void logTransactionFailure(Exception exception) {
        log.error("Transaction Creation Failed: {}", exception.getMessage());
    }

}
