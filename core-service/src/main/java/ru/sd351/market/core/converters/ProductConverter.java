package ru.sd351.market.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sd351.market.api.ProductDto;

import ru.sd351.market.core.entities.Product;
import ru.sd351.market.core.services.CategoryService;


@Component
@RequiredArgsConstructor
public class ProductConverter {
  private final CategoryService categoryService;

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice(), product.getCategory().getTitle());
    }

    /*public Product dtoToEntity(ProductDto productDto) {
        Product p = new Product();
        p.setId(productDto.getId());
        p.setTitle(productDto.getTitle());
        p.setPrice(productDto.getPrice());
        Category c = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Категория не найдена"));
        p.setCategory(c);
        return p;
    }*/
}
