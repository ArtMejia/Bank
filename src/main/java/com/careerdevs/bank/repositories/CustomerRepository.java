package com.careerdevs.bank.repositories;

import com.careerdevs.bank.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByLastName(String lastName);

    List<Customer> findAllByBank_id(Long id);
}
