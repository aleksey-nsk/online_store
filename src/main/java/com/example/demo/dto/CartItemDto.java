package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {

    private String productTitle;
    private String categoryName;
    private BigDecimal pricePerProduct;
    private Integer quantity;
    private BigDecimal value;
}
