package com.example.demo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {

    private int id;
    private String title;
    private BigDecimal cost;

    public Product() {
    }

    public Product(int id, String title, double cost) {
        this.id = id;
        this.title = title;
        this.cost = new BigDecimal(cost).setScale(2, RoundingMode.HALF_UP);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return String.format("id: %s - %s (%s руб.)", id, title, cost);
    }
}
