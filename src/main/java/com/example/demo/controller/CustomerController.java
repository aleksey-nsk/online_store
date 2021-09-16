//package com.example.demo.controller;
//
//import com.example.demo.entity.Customer;
//import com.example.demo.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.List;
//
//@Controller
//public class CustomerController {
//
//    @Autowired
//    private CustomerService customerService;
//
//    @GetMapping(value = "/customer")
//    public String customers(Model model) {
//        List<Customer> customerList = customerService.findAll();
//        model.addAttribute("customerList", customerList);
//        return "customer";
//    }
//
//    @PostMapping(value = "/customer")
//    public String addCustomer(@ModelAttribute Customer customer) {
//        customerService.addCustomer(customer);
//        return "redirect:/customer";
//    }
//}
