//package com.example.demo.dto;
//
//import com.example.demo.entity.Category;
//import lombok.Data;
//
//@Data
//public class CategoryDto {
//
//    private Integer id;
//    private String name;
//
//    public CategoryDto(Integer id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//
//    public static CategoryDto valueOf(Category category) {
//        return new CategoryDto(
//                category.getId(),
//                category.getName()
//        );
//    }
//
//    public Category mapToCategory() {
//        return new Category(id, name);
//    }
//}
