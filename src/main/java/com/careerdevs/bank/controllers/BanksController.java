package com.careerdevs.bank.controllers;

import com.careerdevs.bank.models.Bank;
import com.careerdevs.bank.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@CrossOrigin // for frontend communication
@RestController
@RequestMapping("/api/banks")  // /api/ is good practice for controllers that return data not a webpage
public class BanksController {

    @Autowired
    private BankRepository bankRepository;

    @PostMapping
    public ResponseEntity<?> createOneBank(@RequestBody Bank newBankData) {
        try {
            // validation occurs here
            Bank addedBank = bankRepository.save(newBankData);
            //return ResponseEntity.ok(addedBank); // 200 - success
            return new ResponseEntity<>(addedBank, HttpStatus.CREATED); // 201 creating own body and status code
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage()); // 500 internal error codes
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllBanks() {
        List<Bank> allBanks = bankRepository.findAll();
    //  return new ResponseEntity<>(allBanks, HttpStatus.OK);
    //  return ResponseEntity.ok(bankRepository.findAll());
        return new ResponseEntity<>(bankRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBankById(@PathVariable Long id) {
//      use this instead of try catch, auto sends 404
//      Bank requestedBank = bankRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Optional<Bank> requestedBank = bankRepository.findById(id);
        if (requestedBank.isEmpty()) {
            return new ResponseEntity<>("Invalid ID", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(requestedBank.get(), HttpStatus.OK);
    }

    // Can use PutMapping or PostMapping
    @PostMapping("/{id}")
    public ResponseEntity<?> putOneById(@PathVariable Long id, @RequestBody Bank newBankData) {
        Bank requestedBank = bankRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
//      Option 1    trusting user
//      return ResponseEntity.ok(bankRepository.save(newBankData)); newBankData must include ALL fields

//      Option 2
        if (!newBankData.getName().equals("")) { // Key MUST exist
            requestedBank.setName(newBankData.getName());
        }
        if (newBankData.getPhoneNumber() != null && !newBankData.getPhoneNumber().equals("")) { // Key DOES NOT need to exist, if it does then cannot be empty
            requestedBank.setPhoneNumber(newBankData.getPhoneNumber());
        }
        return ResponseEntity.ok(bankRepository.save(requestedBank));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneById(@PathVariable Long id) {
        // If return is unwanted, the below is negligible - deleteById only fails if void is provided and that cannot be due to path
        Bank requestedBank = bankRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank with ID: " + id + "is not found"));
        bankRepository.deleteById(id);
        return ResponseEntity.ok(requestedBank);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findOneBankByName(@PathVariable String name) {
        Bank requestedBank = bankRepository.findByName(name).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank with Name: " + name + "is not found")
        );
        return new ResponseEntity<>(requestedBank, HttpStatus.OK);
    }

    @GetMapping("/areaCode/{areaCode}")
    public ResponseEntity<?> findBanksByAreaCode(@PathVariable String areaCode) {
        List<Bank> foundBanks = bankRepository.findAllAreaCodes(areaCode);
        return new ResponseEntity<>(foundBanks, HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Bank> findBankByCustomerId(@PathVariable Long id) {
        Bank foundBank = bankRepository.getByCustomers_id(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return new ResponseEntity<>(foundBank, HttpStatus.OK);
    }

}
