package com.example.demo.service.impl;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.specifications.ProductSpecifications;
import com.example.demo.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final static int PAGE_SIZE = 4;

    private final ProductRepository productRepository;
//    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository /*, CategoryRepository categoryRepository */) {
        this.productRepository = productRepository;
//        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<ProductDto> findAll(MultiValueMap<String, String> params, Integer pageIndex) {
//        Page<Product> result = productRepository.findAll(ProductSpecifications.build(params), PageRequest.of(pageIndex, PAGE_SIZE));
//        List<ProductDto> productDtoList = result
//                .stream()
//                .map(it -> ProductDto.valueOf(it))
//                .collect(Collectors.toList());
//
//        log.debug("Список всех товаров из каталога: " + productDtoList);
//        return productDtoList;

        // (pageIndex - 1) потому что тут нумерация с 0, а на фронте с 1
        return productRepository.findAll(ProductSpecifications.build(params), PageRequest.of(pageIndex - 1, PAGE_SIZE))
                .map(it -> ProductDto.valueOf(it));
    }

    @Override
    public ProductDto findById(Integer id) {
//        log.debug("Найти в БД товар с id: " + id);
//        Optional<Product> optionalProduct = productRepository.findById(id);
//        log.debug("optionalProduct: " + optionalProduct);
//
//        if (optionalProduct.isPresent()) {
//            Product product = optionalProduct.get();
//            ProductDto productDto = ProductDto.valueOf(product);
//            return productDto;
//        } else {
//            log.error("По указанному id=" + id + " товар не найден");
//            return null;
//        }

        return productRepository.findById(id)
                .map(it -> ProductDto.valueOf(it))
                .orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public void save(ProductDto productDto) {
        Product product = productDto.mapToProduct();

        productRepository.save(product);
        log.debug("В БД сохранён новый товар: " + product);
    }

//    @Override
//    public void save(ProductDto productDto) {
//        Product product = productDto.mapToProduct();
//
//        Category category = product.getCategory();
//        if (category != null) {
//            String categoryName = category.getName();
//            Category byName = categoryRepository.findByName(categoryName);
//            product.setCategory(byName);
//        }
//
//        productRepository.save(product);
//        log.debug("В БД сохранён новый товар: " + product);
//    }

//    @Override
//    public void deleteById(Integer id) {
//        log.debug("Удалить из БД товар с идентификатором: " + id);
//        productRepository.deleteById(id);
//    }

//    @Override
//    public void updateProduct(Integer id, ProductDto newProductDto) {
//        log.debug("Обновить товар с id: " + id);
//
//        Product oldProduct = productRepository.getById(id);
//        Product newProduct = newProductDto.mapToProduct();
//        log.debug("Текущий товар: " + oldProduct);
//        log.debug("Новый товар: " + newProduct);
//
//        productRepository.save(newProduct);
//    }

//    @Override
//    public List<ProductDto> findSorted(Sorted sorted) {
//        SortedType type = sorted.getType();
//        log.debug("Тип сортировки: " + type);
//
//        Sort.Direction direction;
//        String property;
//
//        switch (type) {
//            case INCREASE:
//                direction = Sort.Direction.ASC;
//                property = "price";
//                break;
//            case DECREASE:
//                direction = Sort.Direction.DESC;
//                property = "price";
//                break;
//            case ALPHABET:
//                direction = Sort.Direction.ASC;
//                property = "title";
//                break;
//            case WITHOUT:
//            default:
//                direction = Sort.Direction.ASC;
//                property = "id";
//                break;
//        }
//
//        List<ProductDto> productDtoList = productRepository.findAll(Sort.by(direction, property))
//                .stream()
//                .map(it -> ProductDto.valueOf(it))
//                .collect(Collectors.toList());
//
//        log.debug("Список отсортированных товаров: " + productDtoList);
//        return productDtoList;
//    }

}
