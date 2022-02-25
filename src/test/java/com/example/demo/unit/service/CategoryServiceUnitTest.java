package com.example.demo.unit.service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.impl.CategoryServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CategoryServiceImpl.class)
@Log4j2
@ActiveProfiles("test")
public class CategoryServiceUnitTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    private Category createCategory(Long id) {
        String name = RandomStringUtils.randomAlphabetic(5);

        Category category = new Category(id, name);
        log.debug("category: " + category);

        return category;
    }

    @Test
    @DisplayName("Успешный поиск всех категорий товаров")
    public void findAllSuccess() {
        CategoryDto categoryDto1 = CategoryDto.valueOf(createCategory(1L));
        CategoryDto categoryDto2 = CategoryDto.valueOf(createCategory(2L));
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.add(categoryDto1);
        categoryDtoList.add(categoryDto2);
        log.debug("categoryDtoList: " + categoryDtoList);

        Category category1 = categoryDto1.mapToCategory();
        Category category2 = categoryDto2.mapToCategory();
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category2);
        log.debug("categoryList: " + categoryList);

        Mockito.doReturn(categoryList)
                .when(categoryRepository).findAll();

        List<CategoryDto> actual = categoryService.findAll();
        log.debug("actual: " + actual);

        assertThat(actual).size().isEqualTo(2);
        assertThat(actual).isEqualTo(categoryDtoList);
    }
}
