package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.sort.Sorted;

import java.util.List;

public interface ProductService {

    List<ProductDto> findAll();

    ProductDto findById(Integer id);

    void save(ProductDto productDto);

    void deleteById(Integer id);

    List<ProductDto> findAllWithSortByPrice();

    List<ProductDto> findSorted(Sorted sorted);
}
