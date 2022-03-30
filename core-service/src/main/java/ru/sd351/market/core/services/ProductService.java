package ru.sd351.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.sd351.market.api.ProductDto;
import ru.sd351.market.core.converters.ProductConverter;
import ru.sd351.market.core.entities.Product;
import ru.sd351.market.core.repositories.ProductRepository;
import ru.sd351.market.core.specifications.ProductSpecifications;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductConverter converter;


    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<ProductDto> findAllProducts(BigDecimal min, BigDecimal max, String titlePart, Integer page) {
        Specification<Product> spec = Specification.where(null);

        if (min != null) {
            //min=BigDecimal.ZERO;
           spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(min));
        }
        if (max != null) {
           // max= BigDecimal.valueOf(Integer.MAX_VALUE);
           spec = spec.and(ProductSpecifications.priceLesserOrEqualsThan(max));
        }
        if (titlePart != null) {
            //titlePart="";
            spec = spec.and(ProductSpecifications.likeTitle(titlePart));
        }
        //return productRepository.findAllByPriceGreaterThanEqualAndPriceLessThanEqualAndTitleContains(min, max, titlePart, PageRequest.of(page-1,5)).map(converter::entityToDto);
        return productRepository.findAll(spec, PageRequest.of(page-1, 5)).map(converter::entityToDto);

    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

   /* public Product createNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        product.setCategory(category);
        productRepository.save(product);
        return product;
    }*/
}
