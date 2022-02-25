package com.example.demo.integration;

import com.example.demo.dto.CategoryDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@ActiveProfiles("test")
public class CategoryControllerIntegrationTest extends BaseIntegrationTest {

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

        mockMvc.perform(get(BASE_CATEGORY_URL))
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

        mockMvc.perform(get(BASE_CATEGORY_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(savedAsJson, true));
    }
}
