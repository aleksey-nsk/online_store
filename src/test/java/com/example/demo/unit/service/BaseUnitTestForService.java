package com.example.demo.unit.service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.impl.CategoryServiceImpl;
import com.example.demo.service.impl.ProductServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest(classes = {ProductServiceImpl.class, CategoryServiceImpl.class})
@Log4j2
@ActiveProfiles("test")
public abstract class BaseUnitTestForService {

    @Autowired
    protected ProductService productService;

    @Autowired
    protected CategoryService categoryService;

    @MockBean
    protected ProductRepository productRepository;

    @MockBean
    protected CategoryRepository categoryRepository;

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
