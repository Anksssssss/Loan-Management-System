package com.Ankssss.LoanManagement.entity;

import com.Ankssss.LoanManagement.constants.LoanStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    private String id;

    @OneToOne
    @JsonIgnore
    private LoanApplication application;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Repayment> repayments;

    private BigDecimal amount;
    private BigDecimal interestRate;
    private Integer tenure;
    private BigDecimal emiAmount;
    private BigDecimal paidAmount;
    private BigDecimal remainingAmount;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
        this.status = LoanStatus.ACTIVE;
        this.paidAmount = new BigDecimal("0");
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

}
