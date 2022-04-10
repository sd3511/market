package ru.sd351.market.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sd351.market.api.CartDto;
import ru.sd351.market.api.StringResponse;
import ru.sd351.market.carts.convertes.CartConverter;
import ru.sd351.market.carts.services.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/generate_uuid")
    public StringResponse generateUuid(){
        return new StringResponse(UUID.randomUUID().toString());
    }


    @GetMapping("/{uuid}/add/{id}")
    public void addToCart(@PathVariable Long id, @PathVariable String uuid, @RequestHeader(required = false) String username) {
        String targetUid = getCartUuid(username,uuid);
        cartService.add(id, targetUid);


    }

    @GetMapping("/{uuid}/clear")
    public void clearCart(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        String targetUid = getCartUuid(username,uuid);
        cartService.clear(targetUid);

    }

    @GetMapping("/{uuid}/remove/{id}")
    public void removeFromCart(@PathVariable Long id, @PathVariable String uuid, @RequestHeader(required = false) String username) {
        String targetUid = getCartUuid(username,uuid);
        cartService.remove(id,targetUid);

    }

    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@RequestHeader(required = false) String username, @PathVariable String uuid) {
       String targetUid = getCartUuid(username,uuid);
        return cartConverter.entityToDto(cartService.getCurrentCart(targetUid));

    }
    
    private String getCartUuid(String username, String uuid){
        if (username!=null){
            return username;
        }
        return uuid;
    }
}
