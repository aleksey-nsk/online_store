package com.example.demo.dto;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProductDto {

    private Integer id;

    @NotEmpty
    private String title;

    @Min(1)
    @Max(1_000_000_000)
    private Integer price;

    @NotNull
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
