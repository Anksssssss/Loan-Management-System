package com.Ankssss.LoanManagement.entity;

import com.Ankssss.LoanManagement.constants.RepaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "repayments")
public class Repayment implements Serializable {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Loan loan;

    @OneToOne(mappedBy = "repayment", cascade = CascadeType.ALL)
    @JsonIgnore
    Transaction transactions;

    private BigDecimal amount;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Enumerated(EnumType.STRING)
    private RepaymentStatus status;

}
