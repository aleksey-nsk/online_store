package com.example.demo.service.impl;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.utils.Sorted;
import com.example.demo.utils.SortedType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> productDtoList = productRepository.findAll()
                .stream()
                .map(it -> ProductDto.valueOf(it))
                .collect(Collectors.toList());
        log.debug("Список всех товаров из каталога: " + productDtoList);
        return productDtoList;
    }

    @Override
    public ProductDto findById(Integer id) {
        log.debug("Найти в БД товар с id: " + id);
        Optional<Product> optionalProduct = productRepository.findById(id);
        log.debug("optionalProduct: " + optionalProduct);


        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            ProductDto productDto = ProductDto.valueOf(product);
            return productDto;
        } else {
//            throw new FindProductByIdException("По указанному id=" + id + " товар не найден");
            log.error("По указанному id=" + id + " товар не найден");
            return null;
        }
    }

    @Override
    public void save(ProductDto productDto) {
        Product product = productDto.mapToProduct();

        Category category = product.getCategory();
        if (category != null) {
            String categoryName = category.getName();
            Category byName = categoryRepository.findByName(categoryName);
            product.setCategory(byName);
        }

        productRepository.save(product);
        log.debug("В БД сохранён новый товар: " + product);
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("Удалить из БД товар с идентификатором: " + id);
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> findSorted(Sorted sorted) {
        SortedType type = sorted.getType();
        log.debug("Тип сортировки: " + type);

        Sort.Direction direction;
        String property;

        switch (type) {
            case INCREASE:
                direction = Sort.Direction.ASC;
                property = "price";
                break;
            case DECREASE:
                direction = Sort.Direction.DESC;
                property = "price";
                break;
            case ALPHABET:
                direction = Sort.Direction.ASC;
                property = "title";
                break;
            case WITHOUT:
            default:
                direction = Sort.Direction.ASC;
                property = "id";
                break;
        }

        List<ProductDto> productDtoList = productRepository.findAll(Sort.by(direction, property))
                .stream()
                .map(it -> ProductDto.valueOf(it))
                .collect(Collectors.toList());

        log.debug("Список отсортированных товаров: " + productDtoList);
        return productDtoList;
    }

    @Override
    public void updateProduct(Integer id, ProductDto newProductDto) {
        log.debug("Обновить товар с id: " + id);

        Product oldProduct = productRepository.getById(id);
        Product newProduct = newProductDto.mapToProduct();
        log.debug("Текущий товар: " + oldProduct);
        log.debug("Новый товар: " + newProduct);

        productRepository.save(newProduct);
    }
}
