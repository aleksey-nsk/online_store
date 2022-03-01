**Проект выложен на AWS**:
- URL: http://onlinestore-env.eba-zt3tmkep.eu-central-1.elasticbeanstalk.com/
- Username: `user1` 
- Password: `pswd1`

# Info:

1. Проект **Интернет Магазин**.

2. Бекэнд реализован в виде **Spring Boot REST API**.

3. В dev-профиле используется БД **PostgreSQL** в контейнере **Docker**. Для миграций используется **Liquibase**.

4. Фронтенд реализован на **AngularJS**. Запущенное приложение на localhost:    
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/01_frontend.png)  

5. Документация к API генерируется с помощью **Swagger**. Для просмотра документации
открыть адрес:
- в dev-профиле: http://localhost:8081/swagger-ui/index.html
- в проде: http://onlinestore-env.eba-zt3tmkep.eu-central-1.elasticbeanstalk.com/swagger-ui/index.html

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
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/05_cart.png)  

8. Реализована валидация данных с помощью **spring-boot-starter-validation**.

9. Написаны тесты (**интеграционные** и **unit**).

10. Используется **Spring Security**.

Подключаем зависимость в pom-файл:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/06_security_pom.png)  

Чтобы CSRF работала с AngularJS необходима
настройка `.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())`. Для преобразования
паролей по **алгоритму bcrypt** используем сайт https://www.browserling.com/tools/bcrypt. Конфигурационный файл
настройки аутентификации для профилей **dev** и **prod**:    
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/07_security_dev.png)  

Далее надо отключить аутентификацию для тестового профиля. Для этого создаём конфигурацию, разрешающую
все запросы. Активируем данную конфигурацию только для **test-профиля** (с помощью аннотации **@Profile**):  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/08_security_test.png)  

# Как развернуть приложение на AWS

1. **AWS (Amazon Web Services)** — коммерческое публичное **облако**, поддерживаемое и развиваемое компанией **Amazon**
с 2006 года. Предоставляет подписчикам услуги как по инфраструктурной модели (виртуальные серверы, ресурсы хранения),
так и платформенного уровня (облачные базы данных, облачное связующее программное обеспечение,
облачные бессерверные вычисления, средства разработки).
  
В значительной степени (наряду с **Google Cloud Platform**) повлияло на формирование концепции **облачных вычислений**
в целом, и определило основные направления развития публичной модели развёртывания. Длительное время было крупнейшим
в мире по выручке публичным облаком, во второй половине 2010-х годов уступив по этому показателю **Azure** от Microsoft,
при этом сохраняя доминирование в сегментах инфраструктурных и платформенных услуг. 

2. Amazon **EC2** (Amazon Elastic Compute Cloud) - один из сервисов AWS, позволяющий пользователю арендовать
виртуальный сервер, который называется **инстанс** (англ. **instance**).

Amazon **RDS** (Amazon Relational Database Service) - позволяет просто настраивать, использовать и масштабировать
реляционные базы данных в облаке.

**EBS** (Elastic Beanstalk) - это надстройка-оркестратор над сервисами Amazon. Он поможет по заданной конфигурации
быстро поднять несколько инстансов нашего приложения, базу данных, связать их с кешем, настроить балансировщик нагрузки
и получить агрегированные логи. Всего несколько простых команд из CLI, и через пару минут у нас готово продакшен-окружение
и настроенное приложение, которое будет само масштабироваться в зависимости от нагрузки. EBS бесплатный, то есть за
услуги оркестрации мы не платим ничего, оплачивается только стоимость инстансов по стандартным тарифам EC2.

3. В данный момент есть локальное приложение, которое крутится на localhost на порту 8081, и взаимодействует
с локальной БД:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/09_schema.png)  

4. Зарегистрироваться на AWS. Далее в своей учётке зайти на **AWS Management Console**:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/10_aws_management_console.png)  
Перейти по ссылке "Elastic Beanstalk". Справа будет выбор регионов (конкретные здания с работающим железом и людьми).
Будем работать с европейским сервисом: **Europe (Frankfurt) eu-central-1**.

Далее нажать на кнопку "Create Application", указать параметры:
- Application name: online_store
- Platform: Java
- Platform branch: Corretto 11 running on 64bit Amazon Linux 2
- Platform version: 3.2.11 (Recommended)
- Application code: Sample application

и затем нажать "Create application":  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/11_create_web_app.png)  

AWS всё создаст (это займёт несколько минут):  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/12_creating.png)  

Готово:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/13_created.png)  
Здесь видим кнопку "Upload and deploy". С помощью этой кнопки потом будем деплоить код. В реальности код 
деплоится на сервер более сложным и более надёжным способом, но для учебных целей сойдёт этот способ.

Видим URL нашего приложения `Onlinestore-env.eba-zt3tmkep.eu-central-1.elasticbeanstalk.com`, по которому
можно перейти:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/14_open_url.png)  

Также можно перейти на страницу с параметрами приложения:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/15_online_store.png)  

5. Следующий шаг - это создание БД. Для этого нажать "Configuration", далее в списке конфигураций
справа от "Database" нажать кнопку "Edit":  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/16_configuration_database.png)  

Выбрать параметры БД, логин/пароль: `prod`/`pswdprod`, и нажать кнопку "Apply":  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/17_db_prop.png)  

В итоге видим, что появился Endpoint:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/18_endpoint.png)  

Нажмём на ссылку возле Endpoint и увидим созданную БД:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/19_db.png)  

Далее нажмём на идентификатор БД и попадём в настройки. Чтобы подключиться к БД из внешнего мира нужна
настройка **VPC security groups** (это настройки доступа к этому сервису). Также видим тут Endpoint и Port:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/20_db_id.png)  

Переходим по ссылке **VPC security groups** и видим некие правила безопасности для нашего соединения.
Далее нажать на "Security group ID":  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/21_security_groups.png)  

Видим некие правила (**Inbound rules**). Нажимаем на кнопку "Edit inbound rules":  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/22_inbound_rules.png)  

В редакторе сначала нажать "Delete", а потом "Add rule". Далее заполнить поля:
- Type: Custom TCP
- Port range: 5432 (порт по которому будем соединяться)
- Source: Anywhere-IPv4

и затем нажать "Save rules":  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/23_edit_inbound_rules.png)  

В итоге в разделе **Inbound rules** увидим, что правило создано:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/24_rule_modified.png)  

6. Теперь надо подключиться к этой БД из IntelliJ IDEA:
- Host: прописываем значение Endpoint
- User/Password: `prod`/`pswdprod`

И затем проверяем кнопкой "Test Connection":  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/25_db_prod.png)  

Далее в проекте в файле **application-prod.yaml** указываем url/username/password, а в **application.yaml**
задаём активный профиль **prod**. Также указываем другой порт для прода: **8082** (в application-prod и js-файле).

Запускаем приложение, открываем в браузере адрес http://localhost:8082/ и видим что приложение работает! В данном
случае само приложение на локальном компе, а БД на AWS:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/26_schema_db_on_aws.png)  

7. Теперь надо всё запушить на AWS, чтобы было так:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/27_schema_all_on_aws.png)  

В js-файле указываем contextPath для профиля prod:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/28_prod_context_path.png)  

В файле **application-prod.yaml** нужно указать порт **5000**. Инфа из инструкции:
_"Настройте приложение для прослушивания на порту 5000, добавив соответствующее свойство 
в application файл. По умолчанию веб-приложение Spring Boot прослушивает запросы по порту 8080. Тем не менее, 
**балансировщик нагрузки AWS**, который используется на платформе Elastic Beanstalk, ожидает что запросы будут
прослушиваться на порту 5000. Без этого значения параметра мы получим ошибку "502 Bad Gateway" при попытке получить 
доступ к нашему приложению через Интернет"_.

Создаём jar-файл командой `mvn clean package`. Далее на AWS надо нажать кнопку "Upload and deploy"
и загрузить созданный jar-ник:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/29_upload_and_deploy.png)  

Открываем адрес приложения http://onlinestore-env.eba-zt3tmkep.eu-central-1.elasticbeanstalk.com
и попадаем на страницу логина `/login`:  
![](https://github.com/aleksey-nsk/online_store/blob/main/screenshots/30_login.png)  
Здесь вводим логин/пароль: `user1`/`pswd1` и попадаем в приложение.

8. **Использованные источники**:
- [Heroku против AWS](https://ru.education-wiki.com/3703940-heroku-vs-aws)
- [Развёртывание Spring Boot приложения на AWS](https://www.youtube.com/watch?v=NKX4mnj0mWQ)
- [Развертывание веб-приложения Java на Elastic Beanstalk](https://learntutorials.net/ru/elastic-beanstalk/topic/9207/%D1%80%D0%B0%D0%B7%D0%B2%D0%B5%D1%80%D1%82%D1%8B%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5-%D0%B2%D0%B5%D0%B1-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F-java-%D0%BD%D0%B0-%D1%8D%D0%BB%D0%B0%D1%81%D1%82%D0%B8%D1%87%D0%BD%D0%BE%D0%BC-%D0%B1%D0%BE%D0%B1%D0%BE%D0%B2%D0%BE%D0%BC-%D1%81%D1%82%D0%B5%D0%B1%D0%BB%D0%B5)
