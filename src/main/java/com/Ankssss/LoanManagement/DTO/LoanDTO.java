package com.Ankssss.LoanManagement.DTO;


import com.Ankssss.LoanManagement.constants.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoanDTO {

    private String loanId;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private Integer tenure;
    private BigDecimal emiAmount;
    private BigDecimal paidAmount;
    private BigDecimal remainingAmount;
    private LoanStatus status;

}
