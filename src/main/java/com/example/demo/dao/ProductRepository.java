package com.example.demo.dao;

import com.example.demo.exception.FindProductByIdException;
import com.example.demo.model.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Log4j2
public class ProductRepository {

    private List<Product> database = new CopyOnWriteArrayList<>(); // in-memory БД в виде списка
    private static AtomicInteger counter = new AtomicInteger(0);

    public ProductRepository() {
    }

    public List<Product> findAll() {
//        return database;
        return Collections.unmodifiableList(database); // лучше возвращать неизменяемую копию
    }

    public Product findById(int id) {
        return database
                .stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElseThrow(() -> new FindProductByIdException("По указанному id=" + id + " товар не найден"));
    }

    public void addProduct(Product product) {
        int id = counter.getAndIncrement();
        product.setId(id);
        log.debug("Сохраним в БД продукт: " + product);
        database.add(product);
    }
}
