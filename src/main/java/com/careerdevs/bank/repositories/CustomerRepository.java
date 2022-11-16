package com.careerdevs.bank.repositories;

import com.careerdevs.bank.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByLastName(String lastName);

    List<Customer> findAllByBank_id(Long id);

    // Optional<Customer> findById(Long aLong);

//    @Query("SELECT u FROM User WHERE u.username = \n"1"\n")
    Optional<Customer> findByUser_username(String username);
}
