package com.example.demo.dto;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import lombok.Data;

@Data
public class ProductDto {

    private Integer id;
    private String title;
    private Integer price;
    private Category category;

    public ProductDto(Integer id, String title, Integer price, Category category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public static ProductDto valueOf(Product product) {
        return new ProductDto(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getCategory()
        );
    }

    public Product mapToProduct() {
        return new Product(id, title, price, category);
    }
}
