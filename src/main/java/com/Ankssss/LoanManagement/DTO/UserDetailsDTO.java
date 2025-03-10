package com.Ankssss.LoanManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDetailsDTO {

    private String userId;
    private String userName;
    private String email;
    private List<AllLoanApplicationsDTO> loanApplications;
    private List<LoanDTO> loans;

}
