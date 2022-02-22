package com.example.demo.controller;

import com.example.demo.dto.CartDto;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartDto find(){
        return cartService.find();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void add(@RequestParam("productId") Long productId){
        cartService.add(productId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(){
        cartService.delete();
    }
}
