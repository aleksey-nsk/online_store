package com.example.demo.exception;

public class FindProductByIdException extends RuntimeException {

    public FindProductByIdException(String msg) {
        super(msg);
    }
}
