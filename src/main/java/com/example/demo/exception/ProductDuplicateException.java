package com.example.demo.exception;

public class ProductDuplicateException extends RuntimeException {

    public ProductDuplicateException(String title) {
        super("В БД уже есть товар с названием: '" + title + "'");
    }
}
