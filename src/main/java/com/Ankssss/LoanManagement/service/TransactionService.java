package com.Ankssss.LoanManagement.service;

import com.Ankssss.LoanManagement.DTO.EmiPaymentResponseDTO;

public interface TransactionService {

    public EmiPaymentResponseDTO processEmiPayment(String repaymentId);

}
