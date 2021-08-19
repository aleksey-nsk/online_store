package ru.geekbrains.spring.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {

    private static int counter = 0;

    private int id;
    private String title;
    private BigDecimal cost;

    public Product() {
    }

    public Product(String title, double cost) {
        this.id = counter++;
        this.title = title;
        this.cost = new BigDecimal(cost).setScale(2, RoundingMode.HALF_UP);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("id: %s - %s (%s руб.)", id, title, cost);
    }
}
