package com.careerdevs.bank.repositories;

import com.careerdevs.bank.models.Bank;
import com.careerdevs.bank.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByLastName(String lastName);
}
