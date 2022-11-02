package com.careerdevs.bank.controllers;

import com.careerdevs.bank.models.Customer;
import com.careerdevs.bank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Customer> createOneCustomer(@RequestBody Customer newCustomerData) {
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

    @PostMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer updatedCustomerData) {
        Customer updateCustomer = customerRepository.save(updatedCustomerData);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }
}
