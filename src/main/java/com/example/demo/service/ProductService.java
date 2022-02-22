package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;

/**
 * @author Aleksey Zhdanov
 * @version 1
 */
public interface ProductService {

    /**
     * <p>Возвращает страницу с товарами</p>
     *
     * @param params    Параметры для фильтрации товаров
     * @param pageIndex Номер страницы
     */
    Page<ProductDto> findProductPage(MultiValueMap<String, String> params, Integer pageIndex);

    /**
     * <p>Возвращает товар по идентификатору</p>
     *
     * @param id Идентификатор
     * @return Товар
     */
    ProductDto findById(Long id);

    ProductDto save(ProductDto productDto);

    /**
     * <p>Обновляет товар</p>
     *
     * @param id         Идентификатор товара
     * @param productDto Данные товара для обновления
     */
    void update(Long id, ProductDto productDto);

    /**
     * <p>Удаляет товар из БД</p>
     *
     * @param id Идентификатор товара
     */
    void delete(Long id);

//    void save(ProductDto productDto);
//
//    void deleteById(Integer id);
//
//    void updateProduct(Integer id, ProductDto newProductDto);
//
//    List<ProductDto> findSorted(Sorted sorted);

}
