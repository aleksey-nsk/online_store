package ru.geekbrains.spring.service;

import ru.geekbrains.spring.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(int id);

    void addProduct(Product product);
}
