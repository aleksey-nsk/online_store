package com.example.demo.dto;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    // @NotEmpty
    private String title;

    // @Min(1)
    // @Max(1_000_000_000)
    private BigDecimal price;

    // @NotNull
    private Category category;

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
