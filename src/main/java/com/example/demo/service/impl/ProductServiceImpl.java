package com.example.demo.service.impl;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.specification.ProductSpecification;
import com.example.demo.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    private final static int PAGE_SIZE = 4;

    @Override
    public Page<ProductDto> findProductPage(MultiValueMap<String, String> params, Integer pageIndex) {
        log.debug("Получить страницу с товарами");
        log.debug("  параметры для фильтрации товаров: " + params);
        log.debug("  номер страницы: " + pageIndex);

        // Создать спецификацию и передать её в какой-либо метод интерфейса JpaSpecificationExecutor
        Specification<Product> spec = ProductSpecification.build(params);
        Pageable pageable = PageRequest.of(pageIndex - 1, PAGE_SIZE); // (pageIndex - 1) потому что тут нумерация с 0, а на фронте с 1
        Page<ProductDto> page = productRepository.findAll(spec, pageable).map(it -> ProductDto.valueOf(it));

        log.debug("  список товаров на искомой странице: " + page.getContent());
        log.debug("  всего товаров на странице: " + page.getContent().size());
        return page;
    }

    @Override
    public ProductDto findById(Long id) {
        ProductDto productDto = productRepository.findById(id)
                .map(it -> ProductDto.valueOf(it))
                .orElseThrow(() -> new ProductNotFoundException(id));

        log.debug("По id=" + id + " получен товар: " + productDto);
        return productDto;
    }

//    @Override
//    public void save(ProductDto productDto) {
//        log.debug("productDto: " + productDto);
//
//        Product product = productDto.mapToProduct();
//
//        productRepository.save(product);
//        log.debug("В БД сохранён новый товар: " + product);
//    }

    @Override
    public ProductDto save(ProductDto productDto) {
        String name = productDto.getCategory().getName();
        Category category = categoryRepository.findByName(name).orElseThrow(() -> new CategoryNotFoundException(name));

        Product product = productDto.mapToProduct();
        product.setCategory(category);
        ProductDto saved = ProductDto.valueOf(productRepository.save(product));
        log.debug("В БД сохранён новый товар: " + saved);

        return saved;
    }

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

    @Override
    public void update(Long id, ProductDto productDto) {
        log.debug("Обновить товар с id: " + id);
        Product currentProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        log.debug("  текущий товар: " + currentProduct);

        Product forUpdate = productDto.mapToProduct();
        log.debug("  данные для обновления: " + forUpdate);

        productRepository.save(forUpdate);
    }

//    @Override
//    public void deleteById(Integer id) {
//        log.debug("Удалить из БД товар с идентификатором: " + id);
//        productRepository.deleteById(id);
//    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        log.debug("Удалить товар: " + product);
        productRepository.deleteById(id);
    }

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
