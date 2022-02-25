package com.example.demo.unit.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.exception.ProductDuplicateException;
import com.example.demo.exception.ProductNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@ActiveProfiles("test")
public class ProductServiceUnitTest extends BaseUnitTestForService {

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
    @DisplayName("Товар в каталог не добавлен (дубликат по названию)")
    public void saveFailDuplicate() {
        // С айдишником
        Category savedCategory = createCategory(4L);
        Product savedProduct = createProduct(21L, savedCategory);

        // Без айдишника
        Category category = new Category(savedCategory.getName());
        log.debug("category: " + category);

        // Без айдишника
        ProductDto productDto = new ProductDto(
                savedProduct.getTitle(),
                BigDecimal.valueOf(77L),
                category
        );
        log.debug("productDto: " + productDto);

        Mockito.doReturn(new Product())
                .when(productRepository).findByTitle(productDto.getTitle());

        try {
            productService.save(productDto);
            throw new RuntimeException("Ожидаемое исключение не было выброшено");
        } catch (ProductDuplicateException e) {
            log.debug(e.getMessage());
            assertThat(e.getMessage()).contains(productDto.getTitle());
            assertThat(e.getMessage()).isEqualTo("В БД уже есть товар с названием: '" + productDto.getTitle() + "'");
        }
    }

    @Test
    @DisplayName("Товар в каталог не добавлен (не найдена категория товара)")
    public void saveFailCategoryNotFound() {
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
