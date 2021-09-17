package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
import com.example.demo.utils.Sorted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDto> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @PostMapping
    public void save(@RequestBody ProductDto productDto) {
        productService.save(productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        productService.deleteById(id);
    }

    // Сортировка
    @PostMapping("/sort")
    public List<ProductDto> findSorted(@RequestBody Sorted sorted) {
        return productService.findSorted(sorted);
    }

    @PutMapping("/{id}")
    public void changePrice(@PathVariable Integer id, @RequestBody ProductDto productDto) {
        productService.changePrice(id, productDto);
    }


//    @GetMapping(value = "/catalog")
//    public String catalog(Model model) {
//        List<Product> productList = productService.findAll();
//        model.addAttribute("productList", productList);
//        return "catalog";
//    }

//    @PostMapping(value = "/catalog")
//    public String addProduct(@ModelAttribute @Valid Product product) {
//        productService.addProduct(product);
//        return "redirect:/catalog";
//    }

//    @GetMapping(value = "/find")
//    public String getFindPage() {
//        return "find";
//    }

//    @PostMapping(value = "/find")
//    public String findProduct(@RequestParam(value = "identifier", required = false) Integer id, Model model) {
//        if (id != null) {
//            Product productById = productService.findById(id);
//            model.addAttribute("product", productById);
//        }
//        return "find";
//    }
}
