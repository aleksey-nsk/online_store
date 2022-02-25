package com.example.demo.unit.controller;

import com.example.demo.dto.CategoryDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@ActiveProfiles("test")
public class CategoryControllerUnitTest extends BaseUnitTestForController {

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

        mockMvc.perform(get(BASE_CATEGORY_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson, true));
    }
}
