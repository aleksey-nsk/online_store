package ru.geekbrains.spring.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.spring.model.Product;
import ru.geekbrains.spring.service.ProductService;

import java.util.List;

@Controller
@Log4j2
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
    public String addProduct(@ModelAttribute Product product) {
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
            log.debug("productById: " + productById);
            model.addAttribute("product", productById);
        }
        return "find";
    }
}
