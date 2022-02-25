package com.example.demo.unit.service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.entity.Category;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@ActiveProfiles("test")
public class CategoryServiceUnitTest extends BaseUnitTestForService {

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
