package com.example.demo.service.impl;

import com.example.demo.dto.CategoryDto;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAll() {
        List<CategoryDto> categoryDtoList = categoryRepository.findAll()
                .stream()
                .map(it -> CategoryDto.valueOf(it))
                .collect(Collectors.toList());
        log.debug("Список всех категорий: " + categoryDtoList);
        return categoryDtoList;
    }
}

/*
@Override
    public List<ProductDto> findAll() {
        List<ProductDto> productDtoList = productRepository.findAll()
                .stream()
                .map(it -> ProductDto.valueOf(it))
                .collect(Collectors.toList());
        log.debug("Список всех товаров из каталога: " + productDtoList);
        return productDtoList;
    }
 */