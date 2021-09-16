package com.example.demo.dto;

import com.example.demo.entity.Product;
import lombok.Data;

@Data
public class ProductDto {

    private Integer id;
    private String title;
    private Integer price;

    public ProductDto(Integer id, String title, Integer price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public static ProductDto valueOf(Product product) {
        return new ProductDto(
                product.getId(),
                product.getTitle(),
                product.getPrice()
        );
    }

    public Product mapToProduct() {
        return new Product(id, title, price);
    }
}
