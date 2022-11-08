package com.careerdevs.bank.models;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String phoneNumber;

    @OneToMany(mappedBy = "bank", fetch = FetchType.LAZY) // if error change to eager
//    @JsonIncludeProperties({"firstName", "lastName", "id"})
    @JsonIgnoreProperties({"email", "age", "location", "bank"})
//    Include is a whitelist, only includes what is entered
//    Ignore is a blacklist, excludes what is entered

    private List<Customer> customers;
    // could use a set instead of list to enforce uniqueness. no duplicates

    public Bank(String name, String location, String phoneNumber) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    // default constructor - not needed if there is another constructor in the class. REQUIRED by JPA
    public Bank() {

    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAreaCode() {
        return phoneNumber.substring(0, 3);  //
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
