package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("message", "Добро пожаловать в интернет-магазин");
        return "index";
    }

    @GetMapping(value = "/catalog")
    public String catalog(Model model) {
        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);
        return "catalog";
    }

    @PostMapping(value = "/catalog")
    public String addProduct(@ModelAttribute @Valid Product product) {
        productService.addProduct(product);
        return "redirect:/catalog";
    }

    @GetMapping(value = "/find")
    public String getFindPage() {
        return "find";
    }

    @PostMapping(value = "/find")
    public String findProduct(@RequestParam(value = "identifier", required = false) Integer id, Model model) {
        if (id != null) {
            Product productById = productService.findById(id);
            model.addAttribute("product", productById);
        }
        return "find";
    }
}
