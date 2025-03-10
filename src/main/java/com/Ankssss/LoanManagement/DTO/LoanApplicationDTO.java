package com.Ankssss.LoanManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoanApplicationDTO {

    @Size(min = 3, max = 3)
    @NotBlank(message = "Credit Score is required")
    private String creditScore;
    private BigDecimal income;
    private BigDecimal requestedAmount;
}
