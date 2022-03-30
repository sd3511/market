package ru.sd351.market.carts.convertes;

import org.springframework.stereotype.Component;
import ru.sd351.market.api.CartItemDto;
import ru.sd351.market.carts.model.CartItem;

@Component
public class CartItemConverter {
    public CartItemDto entityToDto(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setPrice(cartItem.getPrice());
        cartItemDto.setPricePerProduct(cartItem.getPricePerProduct());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setProductTitle(cartItem.getProductTitle());
        cartItemDto.setProductId(cartItem.getProductId());
        return cartItemDto;
    }
}
