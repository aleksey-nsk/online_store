package com.example.demo.service.impl;

import com.example.demo.component.Cart;
import com.example.demo.dto.CartDto;
import com.example.demo.dto.CartItemDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final Cart cart;
    private final ProductService productService;

    @Autowired
    public CartServiceImpl(Cart cart, ProductService productService) {
        this.cart = cart;
        this.productService = productService;
    }

    // Метод для получения нашего Cart-а
    @Override
    public CartDto find() {
//        Map<Long, Integer> products = cart.findAll();

        CartDto result = new CartDto();

        List<CartItemDto> items = cart.findAll()
                .entrySet()
                .stream()
                .map(entry -> {
                    ProductDto productDto = productService.findById(entry.getKey());
                    CartItemDto cartItemDto = new CartItemDto();
                    cartItemDto.setProductTitle(productDto.getTitle());
                    cartItemDto.setPrice(productDto.getPrice());
                    cartItemDto.setQuantity(entry.getValue());
                    cartItemDto.setPrice(entry.getValue() * productDto.getPrice());
                    cartItemDto.setPricePerProduct(productDto.getPrice());
                    return cartItemDto;
                })
                .collect(Collectors.toList());

        result.setItems(items);
        result.setTotalPrice(items.stream().map(it->it.getPrice()).reduce(Integer::sum).orElse(0));
        return result;
    }

    @Override
    public void add(Integer productId) {
        cart.addProduct(productId);
    }

    @Override
    public void deleteAll() {
        cart.deleteAll();
    }
}
