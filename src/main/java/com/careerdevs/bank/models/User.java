package com.careerdevs.bank.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class User {

    @Id

    private String username;

    private String password;
    private String loginToken;

    // @OneToOne(mappedBy = "user")
    // private Customer customer;

    public User(String username, String password, String loginToken) {
        this.username = username;
        this.password = password;
        this.loginToken = loginToken;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
}
