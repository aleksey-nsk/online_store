package com.example.demo.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

/*
Далее займёмся Корзиной (КОРЗИНА хранится в СЕССИОННОМ БИНЕ).

Как должна выглядеть корзина: у каждого пользователя при создании сессии должен появляться
некий сессионный бин, в котором мы будем хранить информацию о покупках пользователя.

Т.е. пользователь работает в браузере, делает некие покупки. Отправляет нам какие-то запросы.
Но мы его выбор не храним в БД. Пока у пользователя сессия активна (например сутки) мы помним что он насохранял.
Сутки пройдут, он перезайдёт в браузер, и его данные из корзины исчезнут.

Указал @Scope(scopeName = SCOPE_SESSION)

Также нужно proxyMode. Потому что каждый раз когда мы будем этот бин автоварить, нам будет вытягиваться другая реализация Cart-а. Поэтому
нужно использовать proxyMode, который будет называться TARGET_CLASS.

В корзине храним список продуктов, которые хочет приобрести пользователь.

В итоге создали хранилище продуктов в специальном сессионном бине.
 */
@Component
@Scope(scopeName = SCOPE_SESSION, proxyMode = TARGET_CLASS)
public class Cart {

    private Map<Long, Integer> products; // id и количество товаров

    public Cart() {
        this.products = new HashMap<>();
    }

    public Map<Long, Integer> getProducts() {
        return Collections.unmodifiableMap(products); // чтобы снаружи нельзя было изменить
    }

    public void add(Long productId) {
        products.merge(productId, 1, (o1, o2) -> o1 + o2);
    }

    public void delete() {
        products.clear();
    }
}
