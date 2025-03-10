package com.Ankssss.LoanManagement.DTO;

import com.Ankssss.LoanManagement.constants.LoanApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CustomerApplicationsDTO {

    private String applicationId;
    private BigDecimal amountRequested;
    private LoanApplicationStatus status;
    private Date appliedAt;
    private Date approvedAt;
    private String loanId;

}
