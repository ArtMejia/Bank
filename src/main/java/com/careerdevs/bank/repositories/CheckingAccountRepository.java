package com.careerdevs.bank.repositories;

import com.careerdevs.bank.models.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {
}
