package com.example.demo.dto;

import com.example.demo.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;

    public static CategoryDto valueOf(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public Category mapToCategory() {
        return new Category(id, name);
    }
}
