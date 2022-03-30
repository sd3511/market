package ru.sd351.market.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sd351.market.api.CartDto;
import ru.sd351.market.carts.convertes.CartConverter;
import ru.sd351.market.carts.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id, @RequestHeader(required = false) String username) {
        if (username != null) {
            cartService.addOnUserCart(id, username);
        } else {
            cartService.addOnTempCart(id);
        }

    }

    @GetMapping("/clear")
    public void clearCart(@RequestHeader(required = false) String username) {
        if (username != null) {
            cartService.clearUserCart(username);
        } else {
            cartService.clearTempCart();

        }
    }

    @GetMapping("/remove/{id}")
    public void removeFromCart(@PathVariable Long id, @RequestHeader(required = false) String username) {
        if (username != null) {
            cartService.removeFromTUserCart(id, username);
        } else {
            cartService.removeFromTempCart(id);
        }

    }

    @GetMapping
    public CartDto getCurrentCart(@RequestHeader(required = false) String username) {
        if (username != null) {
            return cartConverter.entityToDto(cartService.getCurrentUserCart(username));
        } else {
            return cartConverter.entityToDto(cartService.getTempCart());
        }

    }
}
