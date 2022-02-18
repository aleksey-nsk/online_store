package com.example.demo.service;

import com.example.demo.dto.CartDto;

public interface CartService {

    CartDto find();

    void add(Integer productId);

    void deleteAll();
}
