package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // public interface MultiValueMap<K, V> extends Map<K, List<V>>
    // Extension of the Map interface that stores multiple values.
    //
    // Один ключ в Map соответствует одному значению.
    // Здесь используем MultiValueMap - по одному ключу получаем несколько значений.
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDto> findProductPage(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam("pageIndex") Integer pageIndex
    ) {
        return productService.findProductPage(params, pageIndex);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto findById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @PostMapping
    public ProductDto save(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

//    @PostMapping
//    public ProductDto save(@RequestBody /* @Valid */ ProductDto productDto) {
//        return productService.save(productDto);
//    }

//    @PutMapping("/{id}")
//    public void updateProduct(@PathVariable Integer id, @RequestBody /* @Valid */ ProductDto newProductDto) {
//        productService.updateProduct(id, newProductDto);
//    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        productService.update(id, productDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }

//    // Сортировка
//    @PostMapping("/sort")
//    public List<ProductDto> findSorted(@RequestBody Sorted sorted) {
//        return productService.findSorted(sorted);
//    }

}
