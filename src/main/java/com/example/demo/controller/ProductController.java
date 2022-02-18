package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
//import com.example.demo.utils.Sorted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/product")
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<ProductDto> findAll(@RequestParam MultiValueMap<String, String> params, @RequestParam("p") Integer pageIndex) {
        System.out.println("params: " + params);
        System.out.println("pageIndex: " + pageIndex);
        return productService.findAll(params, pageIndex);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @PostMapping
    public void save(@RequestBody /* @Valid */ ProductDto productDto) {
        productService.save(productDto);
    }

//    @DeleteMapping("/{id}")
//    public void deleteById(@PathVariable Integer id) {
//        productService.deleteById(id);
//    }
//
//    @PutMapping("/{id}")
//    public void updateProduct(@PathVariable Integer id, @RequestBody /* @Valid */ ProductDto newProductDto) {
//        productService.updateProduct(id, newProductDto);
//    }
//
//    // Сортировка
//    @PostMapping("/sort")
//    public List<ProductDto> findSorted(@RequestBody Sorted sorted) {
//        return productService.findSorted(sorted);
//    }

}
