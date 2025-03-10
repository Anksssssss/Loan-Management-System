package com.Ankssss.LoanManagement.entity;

import com.Ankssss.LoanManagement.constants.LoanApplicationStatus;
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
@Table(name = "loan_application")
public class LoanApplication implements Serializable {

    @Id
    private String id;

    @ManyToOne
    private User user;

    private String creditScore;

    private BigDecimal income;

    private BigDecimal requestedAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanApplicationStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private  Date updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
        this.status = LoanApplicationStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

}
