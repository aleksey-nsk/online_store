package ru.geekbrains.spring.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.dao.ProductRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("prototype")
public class Cart {

    private Map<Product, Integer> productsInCart;

    @Autowired
    private ProductRepository productRepository;

    public Cart() {
        productsInCart = new ConcurrentHashMap<>();
    }

    public void addProduct(int id) {
        Product product = productRepository.findById(id);
        productsInCart.merge(product, 1, (oldVal, newVal) -> oldVal + newVal); // создать либо объединить
        System.out.println("В корзину был добавлен продукт: " + product.getTitle());
    }

    public void showCart() {
        System.out.println("Текущее содержимое корзины:");

        if (productsInCart.size() == 0) {
            System.out.println("Корзина пуста");
            return;
        }

        for (Product product : productsInCart.keySet()) {
            String productInfo = String.format("%s: %s шт.", product.getTitle(), productsInCart.get(product));
            System.out.println(productInfo);
        }
    }

    public void clearCart() {
        productsInCart.clear();
        System.out.println("Корзина очищена");
    }
}
