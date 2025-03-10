package com.Ankssss.LoanManagement.entity;

import com.Ankssss.LoanManagement.constants.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    private String id;

    @ManyToOne
    private Loan loan;

    @ManyToOne
    private User user;

    @OneToOne
    private Repayment repayment;

    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @PrePersist
    protected void onCreate(){
        this.time = new Date();
        this.status = TransactionStatus.SUCCESSFUL;
    }

}
