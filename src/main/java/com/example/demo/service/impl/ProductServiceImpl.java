package com.example.demo.service.impl;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

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

        log.debug("Добавить в БД новый товар: " + product);
        productRepository.save(product);
        log.debug("В БД сохранён новый товар: " + product);
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("Удалить из БД товар с идентификатором: " + id);
        productRepository.deleteById(id);
    }


//    @Override
//    public Product findById(int id) {
//        final Product product = productRepository.findProductById(id);
//        log.debug("По id=" + id + " найден продукт: " + product);
//        return product;
//
//        return null;
//    }

//    @Override
//    public void addProduct(Product product) {
//        log.debug("Добавить новый продукт в каталог: " + product);
//
//        List<Product> productList = productRepository.findAllProducts();
//        for (Product oneProduct : productList) {
//            if (oneProduct.getTitle().equalsIgnoreCase(product.getTitle())) {
//                log.error("Товар '" + product.getTitle() + "' уже есть в каталоге!");
//                return;
//            }
//        }
//
//        productRepository.addProduct(product);
//    }

}
