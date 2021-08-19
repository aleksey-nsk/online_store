package ru.geekbrains.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.dao.ProductRepository;
import ru.geekbrains.spring.model.Product;
import ru.geekbrains.spring.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void printAll() {
        System.out.println("\nТовары, доступные в интернет-магазине:");
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }
}
