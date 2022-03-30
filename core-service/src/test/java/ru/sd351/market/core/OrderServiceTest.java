package ru.sd351.market.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.sd351.market.api.CartDto;
import ru.sd351.market.api.CartItemDto;
import ru.sd351.market.core.entities.Category;
import ru.sd351.market.core.entities.Order;
import ru.sd351.market.core.entities.OrderItem;
import ru.sd351.market.core.entities.Product;
import ru.sd351.market.core.integrations.CartServiceIntegration;
import ru.sd351.market.core.repositories.OrderRepository;
import ru.sd351.market.core.services.OrderService;
import ru.sd351.market.core.services.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private ProductService productService;
    @MockBean
    private  OrderRepository orderRepository;
    @MockBean
    private  CartServiceIntegration cartServiceIntegration;

    @Test
    public void createOrderTest(){

        CartDto cartDto = new CartDto();
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setQuantity(1);
        cartItemDto.setProductTitle("MOLOKO");
        cartItemDto.setPrice(BigDecimal.valueOf(230));
        cartItemDto.setProductId(13231L);
        cartItemDto.setPricePerProduct(BigDecimal.valueOf(230));
        List<CartItemDto> cartItemDtoList = List.of(cartItemDto);
        cartDto.setItems(cartItemDtoList);
        cartDto.setTotalPrice(BigDecimal.valueOf(230));

        Mockito.doReturn(cartDto).when(cartServiceIntegration).getCurrentCart("bob");

        Product product = new Product();
        Category category = new Category();
        category.setId(23L);
        category.setTitle("ZHIDKOST'");
        product.setCategory(category);
        product.setId(13231L);
        product.setTitle("MOLOKO");
        product.setPrice(BigDecimal.valueOf(230));

        Mockito.doReturn(Optional.of(product)).when(productService).findById(13231L);




        Order order = orderService.createOrder("bob");



        Assertions.assertNull(order.getId());
        Assertions.assertNull(order.getAddress());
        Assertions.assertNull(order.getCreatedAt());

        Assertions.assertEquals(BigDecimal.valueOf(230),order.getTotalPrice());

        Assertions.assertEquals(cartItemDtoList
                .stream()
                .map(p -> new OrderItem(productService.findById(p.getProductId()).get()
                        ,order,p.getQuantity(),p.getPricePerProduct(), p.getPrice()))
                .collect(Collectors.toList()), order.getItems());

        Mockito.verify(orderRepository,Mockito.times(1)).save(Mockito.any());

    }
}
