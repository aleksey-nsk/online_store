package com.example.demo.repository.specification;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

// Для фильтрации по нескольким условиям предназначен Specification.
//
// Можно реализовывать интерфейс Specification явно, но чаще используются вспомогательные классы, которые группируют различные реализации Specification
// и предоставляют удобные методы для обращения к ним:
public class ProductSpecification {

    // Передаём значение параметра. Далее с помощью criteriaBuilder создаём Спецификацию
    private static Specification<Product> priceGreaterOrEqualsThan(int minPrice) {
        Specification<Product> spec = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
        return spec;
    }

    private static Specification<Product> priceLesserOrEqualsThan(int maxPrice) {
        Specification<Product> spec = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
        return spec;
    }

    private static Specification<Product> titleLike(String titlePart) {
        Specification<Product> spec = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + titlePart + "%");
        return spec;
    }

    public static Specification<Product> build(MultiValueMap<String, String> params) {
        Specification<Product> spec = Specification.where(null);

        if (params.containsKey("min_price") && !params.getFirst("min_price").isBlank()) {
            spec = spec.and(ProductSpecification.priceGreaterOrEqualsThan(Integer.parseInt(params.getFirst("min_price"))));
        }

        if (params.containsKey("max_price") && !params.getFirst("max_price").isBlank()) {
            spec = spec.and(ProductSpecification.priceLesserOrEqualsThan(Integer.parseInt(params.getFirst("max_price"))));
        }

        if (params.containsKey("title_part") && !params.getFirst("title_part").isBlank()) {
            spec = spec.and(ProductSpecification.titleLike(params.getFirst("title_part")));
        }

        return spec;
    }
}
