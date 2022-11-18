package com.careerdevs.bank.controllers;

import com.careerdevs.bank.models.Bank;
import com.careerdevs.bank.models.Customer;
import com.careerdevs.bank.models.User;
import com.careerdevs.bank.repositories.BankRepository;
import com.careerdevs.bank.repositories.CustomerRepository;
import com.careerdevs.bank.repositories.UserRepository;
import org.apache.logging.log4j.util.PerformanceSensitive;
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

    @Autowired UserRepository userRepository;

    @PostMapping("/{bankId}/{loginToken}")
    public ResponseEntity<Customer> createOneCustomer(@RequestBody Customer newCustomerData, @PathVariable Long bankId, @PathVariable String loginToken) {
        // Find the bank by ID in the repository
        // If bank doesn't exist return bad request
        // If bank exist add to newCustomerData and save
        User user = userRepository.findByLoginToken(loginToken).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Bank requestedBank = bankRepository.findById(bankId).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST)); // bad request is status code 400
        newCustomerData.setUser(user);
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
    public ResponseEntity<List<Customer>> getByLastName(@PathVariable("lastname") String lastName) {
        List<Customer> requestedCustomer = customerRepository.findAllByLastName(lastName);
        return new ResponseEntity<>(requestedCustomer, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable("id") Long id) {
        customerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Put should never create
    @PutMapping("/update")
//    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer updatedCustomerData) {
//        Customer updateCustomer = customerRepository.save(updatedCustomerData);
//        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
//    }


    @GetMapping("/bank/{bankId}")
    public ResponseEntity<List<Customer>> getAllByBankId(@PathVariable Long bankId) {
        List<Customer> allCustomers = customerRepository.findAllByBank_id(bankId);
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

    @PostMapping("/token/{loginToken}/{bankId}")
    public ResponseEntity<?> getUserByLoginToken(@PathVariable String loginToken, @PathVariable Long id) {
        User requestedUser = userRepository.findByLoginToken(loginToken).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        foundCustomer.setUser(requestedUser);
        customerRepository.save(foundCustomer);
        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }

    @GetMapping("/self/{loginToken}")
    public ResponseEntity<?> getSelfByLoginToken(@PathVariable String loginToken) {
        User foundUser = userRepository.findByLoginToken(loginToken).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Customer foundCustomer = customerRepository.findByUser_username(foundUser.getUsername()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }
}
