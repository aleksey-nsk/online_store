package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/*
1. "JPA Criteria API"

(Статья: https://easyjava.ru/data/jpa/jpa-criteria/)

Примером "программно создаваемых запросов" может служить любой фильтр, который позволяет фильтровать данные по нескольким полям.
Например можно представить себе фильтр людей по: имени, месту работы, адресу проживания, серии и номеру паспорта.
Причём фильтроваться люди могут по любой комбинации этих полей, например по адресу проживания и месту работы, или по имени и адресу проживания.
В терминах JPQL и SQL выразить такой запрос невозможно, потому что нет оператора «выбрать, у которых поле равно такому-то значению или игнорировать условие, если значение не установлено».

Вариантов реализации такого фильтра несколько:
1) можно сделать 16 различных запросов и в зависимости от того, какие значения фильтра установлены, выбирать подходящий запрос.
Но это плохое решение.
2) можно собирать запрос из составляющих, пользуясь тем, что запрос это строка. Недостатков у этого подхода тоже много: абсолютно нечитаемый код запроса в итоге,
высокая вероятность составить кривой запрос, сложность поддержания генератора запроса в актуальном состоянии и т.д.
3) наконец третий и наиболее правильный вариант, это использование "JPA Criteria API".

"JPA Criteria API" - это мощный механизм по генерации динамических и типобезопасных (при использовании Metamodel) запросов, который напрямую поддерживается в Spring Data JPA.
*/

/*
2. "Spring Data JPA Specifications"

(Статья: https://easyjava.ru/spring/spring-data-project/ispolzovanie-jpa-criteria-v-spring-data-jpa/)

Spring Data JPA определяет интерфейс Specification для создания таких предикатов Criteria API, которые можно было бы использовать повторно.
Чтобы использовать Спецификацию с репозиториями, необходимо чтобы репозиторий имел в списке предков интерфейс JpaSpecificationExecutor<T>.

Можно реализовывать интерфейс Specification явно, но чаще используются вспомогательные классы, которые группируют различные реализации Specification
и предоставляют удобные методы для обращения к ним (см. мой класс ProductSpecification).
Поскольку речь идёт о прямом использовании "JPA Criteria API", сложность и гибкость спецификаций может быть сколь угодно высокой.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}
