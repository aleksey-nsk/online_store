package com.example.demo.service;

import com.example.demo.dto.CategoryDto;

import java.util.List;

/**
 * @author Aleksey Zhdanov
 * @version 1
 */
public interface CategoryService {

    /**
     * <p>Возвращает список всех категорий товаров</p>
     *
     * @return Список категорий
     */
    List<CategoryDto> findAll();
}
