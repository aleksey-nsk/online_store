//package com.example.demo.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "purchases")
//public class Purchase { // Покупка
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Integer id;
//
//    @Column(name = "product_id")
//    private Integer productId;
//
//    @Column(name = "title")
//    private String title;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "customer_id")
//    private Customer customer;
//
//    public Purchase() {
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public Integer getProductId() {
//        return productId;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    @JsonIgnore
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    @Override
//    public String toString() {
//        return "Purchase{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                '}';
//    }
//}
