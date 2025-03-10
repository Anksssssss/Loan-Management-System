package com.Ankssss.LoanManagement.DTO;

import com.Ankssss.LoanManagement.constants.LoanApplicationStatus;
import com.Ankssss.LoanManagement.entity.LoanApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AllLoanApplicationsDTO implements Serializable {
    private String applicationId;
    private String userId;
    private String userName;
    private String creditScore;
    private BigDecimal requestedAmount;
    private BigDecimal income;
    private String status;
    private Date createdAt;
    private Date updatedAt;

    public static AllLoanApplicationsDTO fromEntity(LoanApplication loanApplication){
        return new AllLoanApplicationsDTO(
                loanApplication.getId(),
                loanApplication.getUser().getId(),
                loanApplication.getUser().getUserName(),
                loanApplication.getCreditScore(),
                loanApplication.getRequestedAmount(),
                loanApplication.getIncome(),
                loanApplication.getStatus().name(),
                loanApplication.getCreatedAt(),
                loanApplication.getUpdatedAt()
        );
    }

}
