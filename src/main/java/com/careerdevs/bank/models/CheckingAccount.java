package com.careerdevs.bank.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CheckingAccount {

    @Id
    @SequenceGenerator(name = "accountId", initialValue = 100) // todo: allocation size
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountId")
    private Long id;

    private String alias;
    private Long balance;
    private Long fee;

    @ManyToMany
    @JoinTable(
            name = "customers_account",
//            reverse the names
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "checking_account_id")
    )

    @JsonIncludeProperties({"id", "lastName", "bank"})
    private List<Customer> customers = new ArrayList<>();


    public CheckingAccount() {
    }

    public CheckingAccount(String alias, Long balance, Long fee) {
        this.alias = alias;
        this.balance = balance;
        this.fee = fee;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }
}
