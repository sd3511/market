package ru.sd351.market.core.specifications;


import org.springframework.data.jpa.domain.Specification;
import ru.sd351.market.core.entities.Product;

import java.math.BigDecimal;

public class ProductSpecifications {
    public static Specification<Product> priceGreaterOrEqualsThan(BigDecimal price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> priceLesserOrEqualsThan(BigDecimal price) {

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);

    }

    public static Specification<Product> likeTitle(String titlePart) {

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%",titlePart));

    }


}
