//package com.example.demo.controller;
//
//import com.example.demo.exception.FindProductByIdException;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//// Чтобы не писать обработку исключений в каждом контроллере,
//// можно создать единую точку
//@ControllerAdvice
//public class ControllerExceptionHandler {
//
//    @ExceptionHandler(FindProductByIdException.class) // в скобках тип обрабатываемого исключения
//    public String handleFindProductByIdException(FindProductByIdException e, Model model) {
//        String msg = e.getMessage();
//        model.addAttribute("errorMessage", msg);
//        return "error";
//    }
//}
