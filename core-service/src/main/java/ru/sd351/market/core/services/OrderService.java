package ru.sd351.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sd351.market.api.CartDto;
import ru.sd351.market.api.ResourceNotFoundException;
import ru.sd351.market.core.entities.Order;
import ru.sd351.market.core.entities.OrderItem;
import ru.sd351.market.core.integrations.CartServiceIntegration;
import ru.sd351.market.core.repositories.OrderRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final CartServiceIntegration cartServiceIntegration;

    @Transactional
    public Order createOrder(String username) {
        CartDto cartDto = cartServiceIntegration.getCurrentCart(username);
        Order order = new Order();
        order.setUsername(username);
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setItems(cartDto.getItems().stream().map(
                cartItem -> new OrderItem(
                        productService
                                .findById(cartItem.getProductId())
                                .orElseThrow(()->new ResourceNotFoundException("Product not found")),
                        order,
                        cartItem.getQuantity(),
                        cartItem.getPricePerProduct(),
                        cartItem.getPrice()
                )
        ).collect(Collectors.toList()));
        orderRepository.save(order);
        cartServiceIntegration.clear(username);
        return order;
    }
}
