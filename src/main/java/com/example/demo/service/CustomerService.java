package com.example.demo.service;

import com.example.demo.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();

    void addCustomer(Customer customer);
}
