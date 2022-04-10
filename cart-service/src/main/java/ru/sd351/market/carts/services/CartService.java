package ru.sd351.market.carts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.sd351.market.api.ProductDto;
import ru.sd351.market.carts.integrations.ProductServiceIntegration;
import ru.sd351.market.carts.model.Cart;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;
    private Map<String, Cart> carts;
    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;

    @PostConstruct
    public void init() {
        carts = new HashMap<>();

    }

    public Cart getCurrentCart(String uuid) {
        String targetUid = cartPrefix + uuid;
        if (!redisTemplate.hasKey(targetUid)) {
            redisTemplate.opsForValue().set(targetUid, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(targetUid);
    }

    public void add(Long productId, String uuid) {
        ProductDto product = productServiceIntegration.getProductById(productId);
        execute(uuid, cart -> cart.add(product));
    }


    public void remove(Long productId, String uuid) {
        execute(uuid, cart -> cart.remove(productId));
    }


    public void clear(String uuid) {
        execute(uuid, Cart::clear);

    }

    private void execute(String uuid, Consumer<Cart> cartConsumer) {
        Cart cart = getCurrentCart(uuid);
        String targetUid = cartPrefix + uuid;
        cartConsumer.accept(cart);
        redisTemplate.opsForValue().set(targetUid, cart);
    }


}
