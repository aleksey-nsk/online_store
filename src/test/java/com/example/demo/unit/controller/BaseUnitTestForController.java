package com.example.demo.unit.controller;

import com.example.demo.controller.CategoryController;
import com.example.demo.controller.ProductController;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@WebMvcTest(controllers = {ProductController.class, CategoryController.class})
@Log4j2
@ActiveProfiles("test")
public abstract class BaseUnitTestForController {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected ProductService productService;

    @MockBean
    protected CategoryService categoryService;

    @Autowired
    protected ObjectMapper objectMapper;

    protected static final String BASE_PRODUCT_URL = "/api/v1/product";
    protected static final String BASE_CATEGORY_URL = "/api/v1/category";

    protected Category createCategory(Long id) {
        String name = RandomStringUtils.randomAlphabetic(5);

        Category category = new Category(id, name);
        log.debug("category: " + category);

        return category;
    }

    protected Product createProduct(Long id, Category category) {
        String title = RandomStringUtils.randomAlphabetic(10);
        BigDecimal price = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(10L, 1_000L));

        Product product = new Product(id, title, price, category);
        log.debug("product: " + product);

        return product;
    }
}
