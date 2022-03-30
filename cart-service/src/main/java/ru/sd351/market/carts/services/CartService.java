package ru.sd351.market.carts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sd351.market.api.ProductDto;
import ru.sd351.market.carts.integrations.ProductServiceIntegration;
import ru.sd351.market.carts.model.Cart;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private Cart tempCart;
    private Cart userCart;
    private Map<String, Cart> cartMap;

    @PostConstruct
    public void init() {
        tempCart = new Cart();
        cartMap = new HashMap<>();
    }

    public Cart getTempCart() {
        return tempCart;
    }

    public void addOnUserCart(Long productId, String username) {
        ProductDto product = productServiceIntegration.getProductById(productId);
        userCart.add(product);
        cartMap.put(username, userCart);
    }

    public void addOnTempCart(Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId);
        tempCart.add(product);
    }

    public void removeFromTempCart(Long productId) {
        tempCart.remove(productId);
    }

    public void removeFromTUserCart(Long productId, String username) {
        userCart.remove(productId);
        cartMap.put(username, userCart);
    }

    public void clearTempCart() {
        tempCart.clear();
    }

    public void clearUserCart(String username) {
        userCart.clear();
        cartMap.put(username, userCart);
    }


    public Cart getCurrentUserCart(String username) {
        if (cartMap.containsKey(username)) {
            userCart = cartMap.get(username);
            return userCart;
        } else {
            userCart = new Cart();
            cartMap.put(username, userCart);
            return cartMap.get(username);
        }
    }
}
