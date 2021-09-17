package com.example.demo.controller;

import com.example.demo.dto.CategoryDto;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

//    @GetMapping("/{id}")
//    public ProductDto findById(@PathVariable Integer id) {
//        return productService.findById(id);
//    }
//
//    @PostMapping
//    public void save(@RequestBody ProductDto productDto) {
//        productService.save(productDto);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteById(@PathVariable Integer id) {
//        productService.deleteById(id);
//    }
//
//    // Сортировка
//    @PostMapping("/sort")
//    public List<ProductDto> findSorted(@RequestBody Sorted sorted) {
//        return productService.findSorted(sorted);
//    }
//
//    @PutMapping("/{id}")
//    public void updateProduct(@PathVariable Integer id, @RequestBody ProductDto newProductDto) {
//        productService.updateProduct(id, newProductDto);
//    }

}
