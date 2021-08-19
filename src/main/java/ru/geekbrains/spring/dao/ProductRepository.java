package ru.geekbrains.spring.dao;

import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.model.Product;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class ProductRepository {

    private List<Product> database; // in-memory БД в виде списка

    public ProductRepository() {
    }

    @PostConstruct
    public void init() {
        database = new CopyOnWriteArrayList<>();

        database.add(new Product("Батарейки", 5.02));
        database.add(new Product("Клавиатура", 6.50));
        database.add(new Product("Ноутбук", 66.62));
        database.add(new Product("Колонки", 10.00));
        database.add(new Product("Монитор", 42.89));
    }

    public List<Product> findAll() {
        return database;
    }

    public Product findById(int id) {
        return database.get(id);
    }
}
