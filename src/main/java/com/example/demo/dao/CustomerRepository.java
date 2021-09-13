package com.example.demo.dao;

import com.example.demo.model.Customer;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log4j2
public class CustomerRepository {

    public void addCustomer(Customer customer) {
        log.debug("Сохраним в БД покупателя: " + customer);

        // Получаем сессию соединения с нашей БД с помощью Фабрики Сессий
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        // Создаём в рамках сессии транзакцию
        Transaction transaction = session.beginTransaction();

        // Выполняем необходимые действия над данными
        session.save(customer);

        // Сохраняем результаты транзакции в БД
        transaction.commit();

        // Закрываем сессию
        session.close();

        log.debug("В БД был сохранён покупатель: " + customer);
    }

    public List<Customer> findAllCustomers() {
        log.debug("Получить из БД всех покупателей");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT c FROM Customer c");
        List<Customer> customers = query.list();
        log.debug("Получили из БД всех покупателей: " + customers);
        return customers;
    }
}
