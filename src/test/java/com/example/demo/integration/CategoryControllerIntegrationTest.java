package com.example.demo.integration;

import com.example.demo.dto.CategoryDto;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
@ActiveProfiles("test")
public class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String BASE_URL = "/api/v1/category";

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    private Category saveCategoryInDB() {
        String name = RandomStringUtils.randomAlphabetic(5);

        Category category = new Category(name);
        Category savedCategory = categoryRepository.save(category);
        log.debug("savedCategory: " + savedCategory);

        return savedCategory;
    }

    @Test
    @DisplayName("Успешный поиск всех категорий товаров")
    public void findAllSuccess() throws Exception {
        CategoryDto saved1 = CategoryDto.valueOf(saveCategoryInDB());
        CategoryDto saved2 = CategoryDto.valueOf(saveCategoryInDB());

        List<CategoryDto> list = new ArrayList<>();
        list.add(saved1);
        list.add(saved2);
        log.debug("list: " + list);

        String savedAsJson = objectMapper.writeValueAsString(list);
        log.debug("savedAsJson: " + savedAsJson);

        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(savedAsJson, true));
    }

    @Test
    @DisplayName("Успешный поиск пустого списка категорий товаров")
    public void findEmptyList() throws Exception {
        List<CategoryDto> emptyList = new ArrayList<>();
        log.debug("emptyList: " + emptyList);

        String savedAsJson = objectMapper.writeValueAsString(emptyList);
        log.debug("savedAsJson: " + savedAsJson);

        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(savedAsJson, true));
    }
}
