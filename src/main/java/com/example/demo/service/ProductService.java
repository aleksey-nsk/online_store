package com.example.demo.service;

import com.example.demo.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> findAll();

    ProductDto findById(Integer id);

    void save(ProductDto productDto);

    void deleteById(Integer id);
}
