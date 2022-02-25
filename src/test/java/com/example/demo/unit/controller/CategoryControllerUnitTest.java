package com.example.demo.unit.controller;

import com.example.demo.controller.CategoryController;
import com.example.demo.dto.CategoryDto;
import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
@Log4j2
@ActiveProfiles("test")
public class CategoryControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/v1/category";

    private Category createCategory(Long id) {
        String name = RandomStringUtils.randomAlphabetic(5);

        Category category = new Category(id, name);
        log.debug("category: " + category);

        return category;
    }

    @Test
    @DisplayName("Успешный поиск всех категорий товаров")
    public void findAllSuccess() throws Exception {
        CategoryDto created1 = CategoryDto.valueOf(createCategory(1L));
        CategoryDto created2 = CategoryDto.valueOf(createCategory(2L));

        List<CategoryDto> list = new ArrayList<>();
        list.add(created1);
        list.add(created2);
        log.debug("list: " + list);

        String expectedJson = objectMapper.writeValueAsString(list);
        log.debug("expectedJson: " + expectedJson);

        Mockito.doReturn(list)
                .when(categoryService).findAll();

        mockMvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson, true));
    }
}
