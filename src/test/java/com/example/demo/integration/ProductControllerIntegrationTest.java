package com.example.demo.integration;

import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductControllerIntegrationTest {

    @Autowired
    private ProductRepository productRepository;


}
