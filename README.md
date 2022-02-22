# Info:

1. Проект **Интернет Магазин**.

2. Использована БД **Postgres** в контейнере **Docker**.
Настройки контейнера указываем в файле **docker-compose.yaml**.
Настройки подключения к БД прописываем в файле src/main/resources/**application-dev.yaml**.
Для автоматизации внесения обновлений в структуру БД используем **Liquibase**.

Добавил 2 новые миграции
ChangeSet db/changelog/02_table_categories.xml::Create and fill table categories::Aleksey Zhdanov ran successfully in 9ms
ChangeSet db/changelog/03_add_fk_to_products.xml::Add foreign key to table products::Aleksey Zhdanov ran successfully in 8ms



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
- **Развернуть приложение на Heroku !!!**
- **Развернуть приложение на AWS !!!**
- Сделать валидацию полей.
- Приделать **ExceptionHandler**.
- Реализовать новые сущности, и сделать такие связи (**это не буду делать**):  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/02_db_todo.png)  
- Заиспользовать **Spring Security**.
- Все денежные поля сделать типа **BigDecimal**.
