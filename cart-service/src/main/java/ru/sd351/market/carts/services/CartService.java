package ru.sd351.market.carts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private Map<String, Cart> carts;
    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;

    @PostConstruct
    public void init() {
        carts = new HashMap<>();

    }

    public Cart getCurrentCart(String uuid) {
        String targetUid = cartPrefix+uuid;
        if (!carts.containsKey(targetUid)) {
            carts.put(targetUid, new Cart());
        }
        return carts.get(targetUid);
    }

    public void add(Long productId, String uuid) {
        ProductDto product = productServiceIntegration.getProductById(productId);
        getCurrentCart(uuid).add(product);
    }


    public void remove(Long productId, String uuid) {
        getCurrentCart(uuid).remove(productId);
    }


    public void clear(String uuid) {
        getCurrentCart(uuid).clear();
    }



}
