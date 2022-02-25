package com.example.demo.unit.controller;

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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@ActiveProfiles("test")
public class ProductControllerUnitTest extends BaseUnitTestForController {

    @Test
    @DisplayName("Успешный поиск товара по id")
    public void findByIdSuccess() throws Exception {
        Category category = createCategory(10L);
        ProductDto created = ProductDto.valueOf(createProduct(22L, category));

        String expectedJson = objectMapper.writeValueAsString(created);
        log.debug("expectedJson: " + expectedJson);

        Mockito.doReturn(created)
                .when(productService).findById(created.getId());

        mockMvc.perform(get(BASE_PRODUCT_URL + "/" + created.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson, true));
    }

    @Test
    @DisplayName("Товар по id не найден")
    public void findByIdFail() throws Exception {
        Long id = 1L;

        Mockito.doThrow(new ProductNotFoundException(id))
                .when(productService).findById(id);

        mockMvc.perform(get(BASE_PRODUCT_URL + "/" + id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Успешное добавление нового товара в каталог")
    public void saveSuccess() throws Exception {
        // С айдишником
        Category savedCategory = createCategory(1L);
        ProductDto savedProductDto = ProductDto.valueOf(createProduct(2L, savedCategory));

        // Без айдишника
        Category category = new Category(savedCategory.getName());
        ProductDto productDto = new ProductDto(
                savedProductDto.getTitle(),
                savedProductDto.getPrice(),
                category
        );
        log.debug("productDto: " + productDto);

        String savedProductDtoJson = objectMapper.writeValueAsString(savedProductDto);
        String productDtoJson = objectMapper.writeValueAsString(productDto);
        log.debug("savedProductDtoJson: " + savedProductDtoJson);
        log.debug("productDtoJson: " + productDtoJson);

        Mockito.when(productService.save(productDto))
                .thenReturn(savedProductDto);

        mockMvc.perform(post(BASE_PRODUCT_URL).content(productDtoJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(savedProductDtoJson, true));
    }

    @Test
    @DisplayName("Товар в каталог не добавлен (дубликат по названию)")
    public void saveFailDuplicate() throws Exception {
        Category savedCategory = createCategory(1L);
        ProductDto savedProductDto = ProductDto.valueOf(createProduct(2L, savedCategory));

        ProductDto productDto = new ProductDto(
                savedProductDto.getTitle(),
                BigDecimal.valueOf(15L),
                new Category(savedCategory.getName())
        );
        log.debug("productDto: " + productDto);

        String productDtoAsJson = objectMapper.writeValueAsString(productDto);
        log.debug("productDtoAsJson: " + productDtoAsJson);

        Mockito.doThrow(new ProductDuplicateException(productDto.getTitle()))
                .when(productService).save(productDto);

        mockMvc.perform(post(BASE_PRODUCT_URL).content(productDtoAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Товар в каталог не добавлен (не найдена категория товара)")
    public void saveFailCategoryNotFound() throws Exception {
        Category savedCategory = createCategory(1L);
        ProductDto savedProductDto = ProductDto.valueOf(createProduct(2L, savedCategory));

        ProductDto productDto = new ProductDto(
                savedProductDto.getTitle(),
                savedProductDto.getPrice(),
                new Category("Non Existent Category")
        );
        log.debug("productDto: " + productDto);

        String productDtoAsJson = objectMapper.writeValueAsString(productDto);
        log.debug("productDtoAsJson: " + productDtoAsJson);

        Mockito.doThrow(new CategoryNotFoundException(productDto.getCategory().getName()))
                .when(productService).save(productDto);

        mockMvc.perform(post(BASE_PRODUCT_URL).content(productDtoAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Неуспешное обновление цены: товар не найден")
    public void updateFail() throws Exception {
        Category category = createCategory(1L);
        Product product = createProduct(2L, category);

        Long nonExistentProductId = product.getId() + 1L;
        BigDecimal newPrice = BigDecimal.valueOf(50L);
        ProductDto newProduct = new ProductDto(
                nonExistentProductId,
                product.getTitle(),
                newPrice,
                product.getCategory()
        );
        log.debug("newProduct: " + newProduct);

        String newProductAsJson = objectMapper.writeValueAsString(newProduct);
        log.debug("newProductAsJson: " + newProductAsJson);

        Mockito.doThrow(new ProductNotFoundException(nonExistentProductId))
                .when(productService).update(nonExistentProductId, newProduct);

        mockMvc.perform(put(BASE_PRODUCT_URL + "/" + nonExistentProductId).content(newProductAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Товар для удаления не найден")
    public void deleteFail() throws Exception {
        Long id = 1L;

        Mockito.doThrow(new ProductNotFoundException(id))
                .when(productService).delete(id);

        mockMvc.perform(delete(BASE_PRODUCT_URL + "/" + id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
