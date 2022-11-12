package com.careerdevs.bank.repositories;

import com.careerdevs.bank.models.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {
    Set<CheckingAccount> findAllByCustomers_Bank_Id(Long bankId);
}
