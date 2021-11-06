# Info:
1. Проект **Интернет-Магазин**
2. Написан на **Java + Spring Boot + AngularJS**

# Реализованный функционал:
1. В БД две таблицы: **products** (товары) и **categories** (категории). Для работы с данными используется 
**Spring Data JPA**, in-memory database **H2**. Для миграции БД используется **Flyway**.
2. Backend реализован как **REST API**, frontend - на **AngularJS**.
3. Валидация с помощью **spring-boot-starter-validation**.
4. Можно добавлять новые товары в каталог, выбирать их категорию, менять цену товара, удалять, сортировать:
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/01_store_frontend_new.png)

# TODO list:
- Сделать постраничное отображение длинного списка товаров (пагинация).
- Реализовать генерацию Api Documentation с помощью **Swagger**.
- Вместо БД **H2** заиспользовать PostgreSQL в контейнере **Docker**.
- Добавить **интеграционные тесты**.
- Добавить **юнит-тесты**.
- Для приложения и тестирования должны быть разные БД.
- Развернуть приложение на **Heroku**.
- Сделать понятную пользователю валидацию полей на фронтенде.
- Приделать **ExceptionHandler** (реализация есть в видео 10).
- Подключить **Travis CI**.
- Подключить **Codecov**.
- Реализовать новые сущности, и сделать такие связи:
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/02_db_todo.png)
- Заиспользовать **Spring Security**. Сделать 3 роли: админ, юзер, гость. Только админ должен добавлять/удалять/изменять 
товары в каталоге магазина. Юзеры могут регистрироваться. Незарегистрированный гость не может формировать заказы
(то есть добавлять товары в **корзину**), может только просматривать список товаров в каталоге.
- Приделать **Spring Boot Mail Starter** для рассылки почты пользователям (при активации аккаунта).
- Добавить **reCaptcha** при регистрации. Добавить rememberMe и сохранение сессий в БД.
- Все денежные поля сделать типа **BigDecimal**.
