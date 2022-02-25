# Info:

1. Проект **Интернет Магазин**.

2. Бекэнд реализован в виде **Spring Boot REST API**.

3. Используется БД **PostgreSQL** в контейнере **Docker**. Для миграций используется **Liquibase**.

4. Фронтенд реализован на **AngularJS**. Запущенное приложение:    
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/01_frontend.png)  

5. Документация к API генерируется с помощью **Swagger**. Для просмотра документации
открыть адрес http://localhost:8081/swagger-ui/index.html.

6. Реализована **пагинация**, а также **фильтрация** товаров по названию и ценам.

**JPA Criteria API** - это мощный механизм по генерации **динамических**
и типобезопасных (при использовании Metamodel) запросов, который напрямую поддерживается в Spring Data JPA.

Spring Data JPA определяет интерфейс **Specification** для создания таких **предикатов Criteria API**,
которые можно было бы использовать повторно. Чтобы использовать **Спецификацию** с репозиториями, необходимо
чтобы репозиторий имел в списке предков интерфейс **JpaSpecificationExecutor**:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/02_product_repo.png)  

Далее на сервисном слое надо создать спецификацию
и передать её в нужный метод интерфейса JpaSpecificationExecutor:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/03_product_service.png)  

Для фильтрации по нескольким условиям предназначен **Specification**. Можно реализовывать
интерфейс Specification явно, но чаще используются вспомогательные классы, которые группируют различные
реализации Specification и предоставляют удобные методы для обращения к ним. Поскольку речь идёт о
прямом использовании **JPA Criteria API**, сложность и гибкость спецификаций может быть сколь угодно высокой:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/04_product_specification.png)  

7. Корзина реализована как **сессионный бин**:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/01_frontend.png)  

8. Реализовал валидацию данных с помощью **spring-boot-starter-validation**.

9. Написал тесты (**интеграционные** и **unit**).

# TODO list:
- Заиспользовать **Spring Security**.
