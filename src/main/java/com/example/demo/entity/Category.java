//package com.example.demo.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "categories")
//@Data
//public class Category {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Integer id;
//
//    @Column(name = "name")
//    private String name;
//
//    @OneToMany(mappedBy = "category", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    private List<Product> products;
//
//    public Category() {
//    }
//
//    public Category(Integer id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//
//    @JsonIgnore
//    public List<Product> getProducts() {
//        return products;
//    }
//
//    @Override
//    public String toString() {
//        return "Category{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                '}';
//    }
//}
