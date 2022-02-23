package com.example.demo.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Не найден товар по id=" + id);
    }
}
