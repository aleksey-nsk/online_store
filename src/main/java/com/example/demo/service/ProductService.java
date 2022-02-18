package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
//import com.example.demo.utils.Sorted;

import java.util.List;
import java.util.Map;

public interface ProductService {

    Page<ProductDto> findAll(MultiValueMap<String, String> params, Integer pageIndex);

    ProductDto findById(Integer id);

    void save(ProductDto productDto);

//    void save(ProductDto productDto);
//
//    void deleteById(Integer id);
//
//    void updateProduct(Integer id, ProductDto newProductDto);
//
//    List<ProductDto> findSorted(Sorted sorted);
}
