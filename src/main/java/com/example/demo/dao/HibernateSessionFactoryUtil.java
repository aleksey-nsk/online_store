package com.example.demo.dao;

import com.example.demo.model.Customer;
import com.example.demo.model.Product;
import com.example.demo.model.Purchase;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

@Log4j2
public class HibernateSessionFactoryUtil { // утилитный класс для создания Фабрики Сессий (для работы с БД)

    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Создаём объект конфигурации,
                // и передаём ему те классы, которые он должен воспринимать как сущности
                Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
                configuration.addAnnotatedClass(Product.class);
                configuration.addAnnotatedClass(Customer.class);
                configuration.addAnnotatedClass(Purchase.class);

                // Properties - это параметры для работы Hibernate,
                // указанные в файле hibernate.cfg.xml
                Properties properties = configuration.getProperties();

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(properties);

                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        return sessionFactory;
    }
}
