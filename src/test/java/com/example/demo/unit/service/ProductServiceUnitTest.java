package com.example.demo.unit.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.impl.ProductServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ProductServiceImpl.class)
@Log4j2
@ActiveProfiles("test")
public class ProductServiceUnitTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    private Category createCategory(Long id) {
        String name = RandomStringUtils.randomAlphabetic(5);

        Category category = new Category(id, name);
        log.debug("category: " + category);

        return category;
    }

    private Product createProduct(Long id, Category category) {
        String title = RandomStringUtils.randomAlphabetic(10);
        BigDecimal price = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(10L, 1_000L));

        Product product = new Product(id, title, price, category);
        log.debug("product: " + product);

        return product;
    }

    @Test
    @DisplayName("Успешный поиск товара по id")
    public void findByIdSuccess() {
        Long id = 2L;
        ProductDto productDto = ProductDto.valueOf(createProduct(id, createCategory(1L)));
        Product product = productDto.mapToProduct();

        Mockito.doReturn(Optional.of(product))
                .when(productRepository).findById(id);

        ProductDto actual = productService.findById(id);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(productDto);
    }

    @Test
    @DisplayName("Товар по id не найден")
    public void findByIdFail() {
        Long id = 1L;

        Mockito.doReturn(Optional.empty())
                .when(productRepository).findById(id);

        try {
            productService.findById(id);
            throw new RuntimeException("Ожидаемое исключение не было выброшено");
        } catch (ProductNotFoundException e) {
            log.debug(e.getMessage());
            assertThat(e.getMessage()).contains(id.toString());
            assertThat(e.getMessage()).isEqualTo("Не найден товар по id=" + id);
        }
    }

    @Test
    @DisplayName("Успешное добавление нового товара в каталог")
    public void saveSuccess() {
        // С айдишником
        Category savedCategory = createCategory(3L);
        Product savedProduct = createProduct(10L, savedCategory);

        // Без айдишника
        Category category = new Category(savedCategory.getName());
        log.debug("category: " + category);

        // Без айдишника
        ProductDto productDto = new ProductDto(
                savedProduct.getTitle(),
                savedProduct.getPrice(),
                category
        );
        log.debug("productDto: " + productDto);

        // Без айдишника
        Product productWithSavedCategory = new ProductDto(
                savedProduct.getTitle(),
                savedProduct.getPrice(),
                savedCategory
        ).mapToProduct();
        log.debug("productWithSavedCategory: " + productWithSavedCategory);

        Mockito.doReturn(Optional.of(savedCategory))
                .when(categoryRepository).findByName(savedCategory.getName());

        Mockito.when(productRepository.save(productWithSavedCategory))
                .thenReturn(savedProduct);

        ProductDto actual = productService.save(productDto);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(ProductDto.valueOf(savedProduct));
    }

    @Test
    @DisplayName("Товар в каталог не добавлен (не найдена категория товара)")
    public void saveFail() {
        // С айдишником
        Category savedCategory = createCategory(4L);
        Product savedProduct = createProduct(21L, savedCategory);

        // Без айдишника
        String name = "Non Existent Category";
        Category category = new Category(name);
        log.debug("category: " + category);

        // Без айдишника
        ProductDto productDto = new ProductDto(
                savedProduct.getTitle(),
                savedProduct.getPrice(),
                category
        );
        log.debug("productDto: " + productDto);

        Mockito.doReturn(Optional.empty())
                .when(categoryRepository).findByName(name);

        try {
            productService.save(productDto);
            throw new RuntimeException("Ожидаемое исключение не было выброшено");
        } catch (CategoryNotFoundException e) {
            log.debug(e.getMessage());
            assertThat(e.getMessage()).contains(name);
            assertThat(e.getMessage()).isEqualTo("Не найдена категория товара с именем: '" + name + "'");
        }
    }

    @Test
    @DisplayName("Неуспешное обновление цены: товар не найден")
    public void updateFail() {
        Category category = createCategory(5L);
        Product product = createProduct(23L, category);

        Long nonExistentProductId = product.getId() + 1L;
        BigDecimal newPrice = BigDecimal.valueOf(50L);
        ProductDto newProduct = new ProductDto(
                nonExistentProductId,
                product.getTitle(),
                newPrice,
                product.getCategory()
        );
        log.debug("newProduct: " + newProduct);

        Mockito.doReturn(Optional.empty())
                .when(productRepository).findById(nonExistentProductId);

        try {
            productService.update(nonExistentProductId, newProduct);
            throw new RuntimeException("Ожидаемое исключение не было выброшено");
        } catch (ProductNotFoundException e) {
            log.debug(e.getMessage());
            assertThat(e.getMessage()).contains(nonExistentProductId.toString());
            assertThat(e.getMessage()).isEqualTo("Не найден товар по id=" + nonExistentProductId);
        }
    }

    @Test
    @DisplayName("Товар для удаления не найден")
    public void deleteFail() {
        Long id = 1L;

        Mockito.doReturn(Optional.empty())
                .when(productRepository).findById(id);

        try {
            productService.delete(id);
            throw new RuntimeException("Ожидаемое исключение не было выброшено");
        } catch (ProductNotFoundException e) {
            log.debug(e.getMessage());
            assertThat(e.getMessage()).contains(id.toString());
            assertThat(e.getMessage()).isEqualTo("Не найден товар по id=" + id);
        }
    }
}
