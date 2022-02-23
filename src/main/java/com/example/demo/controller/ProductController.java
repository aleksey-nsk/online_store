package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@Api(description = "Контроллер для товаров")
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
    @ApiOperation(value = "Получить страницу с товарами")
    public Page<ProductDto> findProductPage(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam("pageIndex") Integer pageIndex
    ) {
        return productService.findProductPage(params, pageIndex);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Получить товар по id")
    public ProductDto findById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Добавить новый товар в каталог")
    // public ProductDto save(@RequestBody @Valid ProductDto productDto) {
    public ProductDto save(@RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Обновить товар")
    // public void update(@PathVariable Integer id, @RequestBody @Valid ProductDto newProductDto) {
    public void update(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        productService.update(id, productDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Удалить товар")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }
}
