package com.example.demo.dao;

import com.example.demo.exception.FindProductByIdException;
import com.example.demo.model.Product;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log4j2
public class ProductRepository {

    public void addProduct(Product product) {
        log.debug("Сохраним в БД продукт: " + product);

        // Получаем сессию соединения с нашей БД с помощью Фабрики Сессий
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        // Создаём в рамках сессии транзакцию
        Transaction transaction = session.beginTransaction();

        // Выполняем необходимые действия над данными
        session.save(product);

        // Сохраняем результаты транзакции в БД
        transaction.commit();

        // Закрываем сессию
        session.close();

        log.debug("В БД был сохранён продукт: " + product);
    }

    public List<Product> findAllProducts() {
        log.debug("Получить из БД все продукты");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT p FROM Product p");
        List<Product> products = query.list();
        log.debug("Получили из БД все продукты: " + products);
        return products;
    }

    public Product findProductById(int id) {
        log.debug("Получить из БД продукт с id=" + id);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Product product = (Product) session.get(Product.class, id);
        log.debug("Из БД получен продукт: " + product);

        if (product == null) {
            throw new FindProductByIdException("По указанному id=" + id + " товар не найден");
        }

        return product;
    }
}
