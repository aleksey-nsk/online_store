package ru.geekbrains.spring.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.dao.ProductRepository;
import ru.geekbrains.spring.model.Product;
import ru.geekbrains.spring.service.ProductService;

import java.util.List;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        List<Product> productList = productRepository.findAll();
        log.debug("Список всех продуктов из каталога товаров: " + productList);
        return productList;
    }

    @Override
    public Product findById(int id) {
        Product product = productRepository.findById(id);
        return product;

    }

    @Override
    public void addProduct(Product product) {
        log.debug("Добавить новый продукт в каталог: " + product);

        List<Product> productList = productRepository.findAll();
        for (Product oneProduct : productList) {
            if (oneProduct.getTitle().equalsIgnoreCase(product.getTitle())) {
                log.error("Товар '" + product.getTitle() + "' уже есть в каталоге!");
                return;
            }
        }

        productRepository.addProduct(product);
    }
}
