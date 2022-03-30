package ru.sd351.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.sd351.market.api.ProductDto;
import ru.sd351.market.api.ResourceNotFoundException;
import ru.sd351.market.core.converters.ProductConverter;
import ru.sd351.market.core.entities.Product;
import ru.sd351.market.core.services.ProductService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;

/*    @GetMapping
    public List<ProductDto> findAllProducts() {
        return productService.findAll().stream().map(productConverter::entityToDto).collect(Collectors.toList());
    }*/

    @GetMapping
    public Page<ProductDto> findAll(
            @RequestParam(name = "min", required = false) BigDecimal min,
            @RequestParam(name = "max", required = false) BigDecimal max,
            @RequestParam(name = "title", required = false) String titlePart,
            @RequestParam(name = "p", defaultValue = "1") Integer page) {
        if (page<1){
            page = 1;
        }
        return productService.findAllProducts(min, max, titlePart, page);
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        Product p = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + id));
        return productConverter.entityToDto(p);
    }

 /*   @PostMapping
    public ProductDto createNewProduct(@RequestBody ProductDto productDto) {
        Product p = productService.createNewProduct(productDto);
        return productConverter.entityToDto(p);
    }*/

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
