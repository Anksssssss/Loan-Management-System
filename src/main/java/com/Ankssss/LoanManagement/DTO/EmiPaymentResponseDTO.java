package com.Ankssss.LoanManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class EmiPaymentResponseDTO {

    private TransactionDetails transaction;
    private RepaymentDetails repayment;
    private LoanDetails loan;

    @Data
    @AllArgsConstructor
    public static class TransactionDetails{
        private String status;
        private BigDecimal amountPaid;
    }

    @Data
    @AllArgsConstructor
    public static class RepaymentDetails{
        private String status;
        private String repaymentId;
        private String dueDate;
        private String paymentDate;
    }

    @Data
    @AllArgsConstructor
    public static class LoanDetails{
        private String loanId;
        private BigDecimal remainingAmount;
        private BigDecimal totalPaidAmount;
        private String status;
    }

}
