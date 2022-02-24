package com.example.demo.integration;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
@ActiveProfiles("test")
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String BASE_URL = "/api/v1/product";

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    private Category saveCategoryInDB() {
        String name = RandomStringUtils.randomAlphabetic(5);

        Category category = new Category(name);
        Category savedCategory = categoryRepository.save(category);
        log.debug("savedCategory: " + savedCategory);

        return savedCategory;
    }

    private Product saveProductInDB(Category category) {
        String title = RandomStringUtils.randomAlphabetic(10);
        BigDecimal price = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(10L, 1_000L));

        Product product = new Product(title, price, category);
        Product savedProduct = productRepository.save(product);
        log.debug("savedProduct: " + savedProduct);

        return savedProduct;
    }

    private Product createProduct(Category category) {
        String title = RandomStringUtils.randomAlphabetic(10);
        BigDecimal price = BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(10L, 1_000L));

        Product product = new Product(title, price, category);
        log.debug("product: " + product);

        return product;
    }

    @Test
    @DisplayName("Успешный поиск первой страницы с товарами")
    public void findFirstPageSuccess() throws Exception {
        Category category1 = saveCategoryInDB();
        Category category2 = saveCategoryInDB();

        Product saved1 = saveProductInDB(category1);
        Product saved2 = saveProductInDB(category2);

        mockMvc.perform(get(BASE_URL + "?pageIndex=1"))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.content[0].id").value(saved1.getId()))
                .andExpect(jsonPath("$.content[0].title").value(saved1.getTitle()))
                .andExpect(jsonPath("$.content[0].price").isNumber())
                .andExpect(jsonPath("$.content[0].category.id").value(saved1.getCategory().getId()))
                .andExpect(jsonPath("$.content[0].category.name").value(saved1.getCategory().getName()))

                .andExpect(jsonPath("$.content[1].id").value(saved2.getId()))
                .andExpect(jsonPath("$.content[1].title").value(saved2.getTitle()))
                .andExpect(jsonPath("$.content[1].price").isNumber())
                .andExpect(jsonPath("$.content[1].category.id").value(saved2.getCategory().getId()))
                .andExpect(jsonPath("$.content[1].category.name").value(saved2.getCategory().getName()))

                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(4))
                .andExpect(jsonPath("$.first").value("true"))
                .andExpect(jsonPath("$.last").value("true"))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.numberOfElements").value(2))
                .andExpect(jsonPath("$.size").value(4))
                .andExpect(jsonPath("$.empty").value("false"));
    }

    @Test
    @DisplayName("Успешный поиск второй страницы с товарами")
    public void findSecondPageSuccess() throws Exception {
        Category category1 = saveCategoryInDB();
        Category category2 = saveCategoryInDB();

        Product saved1 = saveProductInDB(category1);
        Product saved2 = saveProductInDB(category1);
        Product saved3 = saveProductInDB(category1);
        Product saved4 = saveProductInDB(category1);
        Product saved5 = saveProductInDB(category2);

        mockMvc.perform(get(BASE_URL + "?pageIndex=2"))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.content[0].id").value(saved5.getId()))
                .andExpect(jsonPath("$.content[0].title").value(saved5.getTitle()))
                .andExpect(jsonPath("$.content[0].price").isNumber())
                .andExpect(jsonPath("$.content[0].category.id").value(saved5.getCategory().getId()))
                .andExpect(jsonPath("$.content[0].category.name").value(saved5.getCategory().getName()))

                .andExpect(jsonPath("$.pageable.pageNumber").value(1))
                .andExpect(jsonPath("$.pageable.pageSize").value(4))
                .andExpect(jsonPath("$.first").value("false"))
                .andExpect(jsonPath("$.last").value("true"))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.totalElements").value(5))
                .andExpect(jsonPath("$.number").value(1))
                .andExpect(jsonPath("$.numberOfElements").value(1))
                .andExpect(jsonPath("$.size").value(4))
                .andExpect(jsonPath("$.empty").value("false"));
    }

    @Test
    @DisplayName("Успешный поиск товара по id")
    public void findByIdSuccess() throws Exception {
        Category category = saveCategoryInDB();
        ProductDto saved = ProductDto.valueOf(saveProductInDB(category));

        String savedAsJson = objectMapper.writeValueAsString(saved);
        log.debug("savedAsJson: " + savedAsJson);

        mockMvc.perform(get(BASE_URL + "/" + saved.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(savedAsJson, true));
    }

    @Test
    @DisplayName("Товар по id не найден")
    public void findByIdFail() throws Exception {
        mockMvc.perform(get(BASE_URL + "/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Успешное добавление нового товара в каталог")
    public void saveSuccess() throws Exception {
        Category category = saveCategoryInDB();
        ProductDto productDto = ProductDto.valueOf(createProduct(category));

        productDto.getCategory().setId(null);
        log.debug("productDto: " + productDto);

        String productDtoAsJson = objectMapper.writeValueAsString(productDto);
        log.debug("productDtoAsJson: " + productDtoAsJson);

        mockMvc.perform(post(BASE_URL).content(productDtoAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value(productDto.getTitle()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()))
                .andExpect(jsonPath("$.category.id").isNumber())
                .andExpect(jsonPath("$.category.name").value(productDto.getCategory().getName()));

        assertThat(productRepository.findAll().size()).isEqualTo(1);
        assertThat(categoryRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Товар в каталог не добавлен (дубликат по названию)")
    public void saveFailDuplicate() throws Exception {
        Category category = saveCategoryInDB();
        Product product = saveProductInDB(category);

        ProductDto productDto = ProductDto.valueOf(createProduct(category));
        productDto.setTitle(product.getTitle());
        productDto.getCategory().setId(null);
        log.debug("productDto: " + productDto);

        String productDtoAsJson = objectMapper.writeValueAsString(productDto);
        log.debug("productDtoAsJson: " + productDtoAsJson);

        mockMvc.perform(post(BASE_URL).content(productDtoAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

        assertThat(productRepository.findAll().size()).isEqualTo(1);
        assertThat(categoryRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Товар в каталог не добавлен (не найдена категория товара)")
    public void saveFailCategoryNotFound() throws Exception {
        Category category = saveCategoryInDB();
        ProductDto productDto = ProductDto.valueOf(createProduct(category));

        productDto.getCategory().setId(null);
        productDto.getCategory().setName("Non Existent Category");
        log.debug("productDto: " + productDto);

        String productDtoAsJson = objectMapper.writeValueAsString(productDto);
        log.debug("productDtoAsJson: " + productDtoAsJson);

        mockMvc.perform(post(BASE_URL).content(productDtoAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        assertThat(productRepository.findAll().size()).isEqualTo(0);
        assertThat(categoryRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Успешное обновление цены товара")
    public void updateSuccess() throws Exception {
        Category category = saveCategoryInDB();
        Product saved = saveProductInDB(category);
        Long id = saved.getId();

        BigDecimal newPrice = BigDecimal.valueOf(50L);
        ProductDto newProduct = new ProductDto(
                saved.getId(),
                saved.getTitle(),
                newPrice,
                saved.getCategory()
        );
        log.debug("newProduct: " + newProduct);

        String newProductAsJson = objectMapper.writeValueAsString(newProduct);
        log.debug("newProductAsJson: " + newProductAsJson);

        mockMvc.perform(put(BASE_URL + "/" + id).content(newProductAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(productRepository.findAll().size()).isEqualTo(1);
        assertThat(categoryRepository.findAll().size()).isEqualTo(1);

        Product updated = productRepository.findById(id).get();
        assertThat(updated.getTitle()).isEqualTo(saved.getTitle());
        assertThat(updated.getPrice().compareTo(newPrice)).isEqualTo(0);
        assertThat(updated.getCategory().getId()).isEqualTo(saved.getCategory().getId());
        assertThat(updated.getCategory().getName()).isEqualTo(saved.getCategory().getName());
    }

    @Test
    @DisplayName("Неуспешное обновление цены: товар не найден")
    public void updateFail() throws Exception {
        Category category = saveCategoryInDB();
        Product saved = saveProductInDB(category);

        Long nonExistentProductId = saved.getId() + 1L;
        BigDecimal newPrice = BigDecimal.valueOf(50L);
        ProductDto newProduct = new ProductDto(
                nonExistentProductId,
                saved.getTitle(),
                newPrice,
                saved.getCategory()
        );
        log.debug("newProduct: " + newProduct);

        String newProductAsJson = objectMapper.writeValueAsString(newProduct);
        log.debug("newProductAsJson: " + newProductAsJson);

        mockMvc.perform(put(BASE_URL + "/" + nonExistentProductId).content(newProductAsJson).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        assertThat(productRepository.findAll().size()).isEqualTo(1);
        assertThat(categoryRepository.findAll().size()).isEqualTo(1);

        Product notUpdated = productRepository.findById(saved.getId()).get();
        assertThat(notUpdated.getTitle()).isEqualTo(saved.getTitle());
        assertThat(notUpdated.getPrice().compareTo(saved.getPrice())).isEqualTo(0);
        assertThat(notUpdated.getCategory().getId()).isEqualTo(saved.getCategory().getId());
        assertThat(notUpdated.getCategory().getName()).isEqualTo(saved.getCategory().getName());
    }

    @Test
    @DisplayName("Успешное удаление товара")
    public void deleteSuccess() throws Exception {
        Long id = saveProductInDB(saveCategoryInDB()).getId();

        assertThat(productRepository.findAll().size()).isEqualTo(1);
        assertThat(categoryRepository.findAll().size()).isEqualTo(1);

        mockMvc.perform(delete(BASE_URL + "/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThat(productRepository.findAll().size()).isEqualTo(0);
        assertThat(categoryRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Товар для удаления не найден")
    public void deleteFail() throws Exception {
        Product saved = saveProductInDB(saveCategoryInDB());
        Long nonExistentProductId = saved.getId() + 1L;

        assertThat(productRepository.findAll().size()).isEqualTo(1);
        assertThat(categoryRepository.findAll().size()).isEqualTo(1);

        mockMvc.perform(delete(BASE_URL + "/" + nonExistentProductId))
                .andDo(print())
                .andExpect(status().isNotFound());

        assertThat(productRepository.findAll().size()).isEqualTo(1);
        assertThat(categoryRepository.findAll().size()).isEqualTo(1);
    }
}
