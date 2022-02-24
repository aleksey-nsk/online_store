package com.example.demo.service.impl;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.exception.ProductDuplicateException;
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

    @Override
    public ProductDto save(ProductDto productDto) {
        log.debug("Сохранить новый товар в БД");
        log.debug("  productDto: " + productDto);

        String title = productDto.getTitle();
        if (productRepository.findByTitle(title) != null) {
            throw new ProductDuplicateException(title);
        }

        String name = productDto.getCategory().getName();
        Category category = categoryRepository.findByName(name).orElseThrow(() -> new CategoryNotFoundException(name));
        log.debug("  category: " + category);

        Product product = productDto.mapToProduct();
        log.debug("  product: " + product);
        product.setCategory(category);
        log.debug("  product after set category: " + product);

        Product savedProduct = productRepository.save(product);
        log.debug("  savedProduct: " + savedProduct);

        ProductDto savedProductDto = ProductDto.valueOf(savedProduct);
        log.debug("  в БД сохранён новый товар: " + savedProductDto);
        return savedProductDto;
    }

    @Override
    public void update(Long id, ProductDto productDto) {
        log.debug("Обновить товар с id: " + id);

        Product currentProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        log.debug("  текущий товар: " + currentProduct);

        Product forUpdate = productDto.mapToProduct();
        log.debug("  данные для обновления: " + forUpdate);

        productRepository.save(forUpdate);
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        log.debug("Удалить товар: " + product);
        productRepository.deleteById(id);
    }
}
