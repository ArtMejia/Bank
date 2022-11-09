package com.careerdevs.bank.controllers;

import com.careerdevs.bank.models.CheckingAccount;
import com.careerdevs.bank.repositories.CheckingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/checking")
public class CheckingAccountController {


    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @PostMapping
    public ResponseEntity<?> addCheckingAccountToDB(@RequestBody CheckingAccount newCheckingAccountData) {
        CheckingAccount addedCheckingAccount = checkingAccountRepository.save(newCheckingAccountData);
        return new ResponseEntity<>(addedCheckingAccount, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllCheckingAccounts() {
        List<CheckingAccount> allCheckingAccounts = checkingAccountRepository.findAll();
        return new ResponseEntity<>(checkingAccountRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCheckingAccountById(@PathVariable Long id) {
        CheckingAccount requestedCheckingAccounts = checkingAccountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(requestedCheckingAccounts, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateCheckingAccountById(@PathVariable Long id, @RequestBody CheckingAccount newCheckingAccountData) {
        CheckingAccount requestedCheckingAccount = checkingAccountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!newCheckingAccountData.getAlias().equals("")) {
            requestedCheckingAccount.setAlias(newCheckingAccountData.getAlias());
        }
        return ResponseEntity.ok(checkingAccountRepository.save(requestedCheckingAccount));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCheckingAccountById(@PathVariable Long id) {
        CheckingAccount requestedCheckingAccount = checkingAccountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        checkingAccountRepository.delete(requestedCheckingAccount);
        checkingAccountRepository.deleteById(id);
        return ResponseEntity.ok(requestedCheckingAccount);
    }
}