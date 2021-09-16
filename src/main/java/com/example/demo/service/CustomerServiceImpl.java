//package com.example.demo.service.impl;
//
//import com.example.demo.repository.CustomerRepository;
//import com.example.demo.entity.Customer;
//import com.example.demo.service.CustomerService;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Log4j2
//public class CustomerServiceImpl implements CustomerService {
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Override
//    public List<Customer> findAll() {
//        List<Customer> customerList = customerRepository.findAllCustomers();
//        log.debug("Список всех покупателей: " + customerList);
//        return customerList;
//    }
//
//    @Override
//    public void addCustomer(final Customer customer) {
//        log.debug("Добавить нового покупателя: " + customer);
//
//        List<Customer> customerList = customerRepository.findAllCustomers();
//        for (Customer oneCustomer : customerList) {
//            if (oneCustomer.getName().equalsIgnoreCase(customer.getName())) {
//                log.error("Покупатель '" + customer.getName() + "' уже зарегистрирован в магазине!");
//                return;
//            }
//        }
//
//        customerRepository.addCustomer(customer);
//    }
//}
