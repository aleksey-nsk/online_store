package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Integer price; // все деньги BigDecimal !!!!!!!!!!!!!!!!!!!

//    @CreatedDate
//    private LocalDateTime createdDateTime;
//
//    @LastModifiedDate
//    private LocalDateTime updatedDateTime;

//    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinColumn(name = "category_id")
//    private Category category;

    public Product() {
    }

    public Product(Integer id, String title, Integer price /* , Category category */ ) {
        this.id = id;
        this.title = title;
        this.price = price;
//        this.category = category;
    }

//    @JsonIgnore
//    public Category getCategory() {
//        return category;
//    }

}
