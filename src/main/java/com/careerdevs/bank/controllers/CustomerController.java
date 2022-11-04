package com.careerdevs.bank.controllers;

import com.careerdevs.bank.models.Bank;
import com.careerdevs.bank.models.Customer;
import com.careerdevs.bank.repositories.BankRepository;
import com.careerdevs.bank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankRepository bankRepository;

    @PostMapping("/{bankId}")
    public ResponseEntity<Customer> createOneCustomer(@RequestBody Customer newCustomerData, @PathVariable Long bankId) {
        // Find the bank by ID in the repository
        // If bank doesn't exist return bad request
        // If bank exist add to newCustomerData and save
        Bank requestedBank = bankRepository.findById(bankId).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST)); // bad request is status code 400
        newCustomerData.setBank(requestedBank);
        Customer newCustomer = customerRepository.save(newCustomerData);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> allCustomers = customerRepository.findAll();
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") Long id) {
        Optional<Customer> requestedCustomer = customerRepository.findById(id);
        return new ResponseEntity<>(requestedCustomer, HttpStatus.OK);
    }

    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<Customer> getByLastName(@PathVariable("lastname") String lastName) {
        Customer requestedCustomer = customerRepository.findByLastName(lastName);
        return new ResponseEntity<Customer>(requestedCustomer, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable("id") Long id) {
        customerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer updatedCustomerData) {
        Customer updateCustomer = customerRepository.save(updatedCustomerData);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

    @GetMapping("/bank/{bankId}")
    public ResponseEntity<List<Customer>> getAllByBankId(@PathVariable Long bankId) {
        List<Customer> allCustomers = customerRepository.findAllByBank_id(bankId);
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }
}
