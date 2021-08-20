package ru.geekbrains.spring.exception;

public class FindProductByIdException extends RuntimeException {

    public FindProductByIdException(String msg) {
        super(msg);
    }
}
