package com.example.demo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer { // Покупатель

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> purchases;

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", purchases=" + purchases +
                '}';
    }
}
