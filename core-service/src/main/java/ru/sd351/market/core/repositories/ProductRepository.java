package ru.sd351.market.core.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.sd351.market.core.entities.Product;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Page<Product> findAllByPriceGreaterThanEqualAndPriceLessThanEqualAndTitleContains(BigDecimal min, BigDecimal max, String name, Pageable pageable);


}
