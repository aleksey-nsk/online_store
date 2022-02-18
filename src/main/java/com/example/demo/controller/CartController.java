package com.example.demo.controller;

import com.example.demo.dto.CartDto;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CartDto find(){
        return cartService.find();
    }

    @PutMapping
    public void add(@RequestParam Integer productId){
        cartService.add(productId);
    }

    @DeleteMapping
    public void clear(){
        cartService.deleteAll();
    }
}
