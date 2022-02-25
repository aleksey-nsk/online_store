package com.example.demo.integration;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    protected static final String BASE_PRODUCT_URL = "/api/v1/product";
    protected static final String BASE_CATEGORY_URL = "/api/v1/category";

    @AfterEach
    protected void tearDown() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    protected Category saveCategoryInDB() {
        String name = RandomStringUtils.randomAlphabetic(5);

        Category category = new Category(name);
        Category savedCategory = categoryRepository.save(category);
        log.debug("savedCategory: " + savedCategory);

        return savedCategory;
    }

    protected Product saveProductInDB(Category category) {
        String title = RandomStringUtils.randomAlphabetic(10);
        BigDecimal price = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(10L, 1_000L));

        Product product = new Product(title, price, category);
        Product savedProduct = productRepository.save(product);
        log.debug("savedProduct: " + savedProduct);

        return savedProduct;
    }

    protected Product createProduct(Category category) {
        String title = RandomStringUtils.randomAlphabetic(10);
        BigDecimal price = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(10L, 1_000L));

        Product product = new Product(title, price, category);
        log.debug("product: " + product);

        return product;
    }
}
