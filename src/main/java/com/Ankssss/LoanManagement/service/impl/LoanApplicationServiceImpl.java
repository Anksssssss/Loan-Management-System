package com.Ankssss.LoanManagement.service.impl;

import com.Ankssss.LoanManagement.DTO.AllLoanApplicationsDTO;
import com.Ankssss.LoanManagement.DTO.LoanApplicationDTO;
import com.Ankssss.LoanManagement.constants.LoanApplicationStatus;
import com.Ankssss.LoanManagement.entity.LoanApplication;
import com.Ankssss.LoanManagement.entity.User;
import com.Ankssss.LoanManagement.repository.LoanApplicationRepository;
import com.Ankssss.LoanManagement.repository.UserRepository;
import com.Ankssss.LoanManagement.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Override
    public String applyForLoan(LoanApplicationDTO dto, String email) {
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new RuntimeException("User not found");
        }
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setId(UUID.randomUUID().toString());
        loanApplication.setIncome(dto.getIncome());
        loanApplication.setCreditScore(dto.getCreditScore());
        loanApplication.setRequestedAmount(dto.getRequestedAmount());
        loanApplication.setStatus(LoanApplicationStatus.PENDING);
        user.getApplications().add(loanApplication);
        userRepository.save(user);
        loanApplication.setUser(user);
        LoanApplication savedApplication = loanApplicationRepository.save(loanApplication);
        return savedApplication.getId();
    }

    @Override
    public List<AllLoanApplicationsDTO> getLoanApplications(String status) {
        List<LoanApplication> applications;
        if(status!=null){
            applications = loanApplicationRepository.findByStatus(LoanApplicationStatus.valueOf(status.toUpperCase()));
        }else{
            applications = loanApplicationRepository.findAll();
        }
        return applications.stream()
                .map(application-> AllLoanApplicationsDTO.fromEntity(application))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "loanApplications", key = "#userId + '_' + (#status != null ? #status : 'ALL')")
    public List<AllLoanApplicationsDTO> getLoanApplicationsByUserId(String userId, String status) {
        List<LoanApplication> applications;
        if(status != null){
            applications = loanApplicationRepository.findByUserIdAndStatus(userId, LoanApplicationStatus.valueOf(status.toUpperCase()));
        }else {
            applications = loanApplicationRepository.findByUserId(userId);
        }
        if(applications == null){
            throw new IllegalArgumentException("invalid userId or status");
        }
        return applications.stream()
                .map(application-> AllLoanApplicationsDTO.fromEntity(application))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "loanApplications", key = "#loanApplication.userId + '_ALL'", allEntries = true)
    public LoanApplication updateStatus(String applicationId, LoanApplicationStatus status) {
        LoanApplication loanApplication = loanApplicationRepository.findById(applicationId)
                .orElseThrow(()-> new EntityNotFoundException("Loan application not found"));
        if(loanApplication.getStatus()!=LoanApplicationStatus.PENDING){
            throw new IllegalArgumentException("Only pending applications can be rejected or accepted");
        }
        loanApplication.setStatus(status);
        AllLoanApplicationsDTO dto = AllLoanApplicationsDTO.fromEntity(loanApplication);
        return loanApplicationRepository.save(loanApplication);
        //return dto;
    }
}
