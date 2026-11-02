package com.example.spring.test.cart.controller;

import com.example.spring.test.cart.entity.Cart;
import com.example.spring.test.cart.request.CreateCartRequest;
import com.example.spring.test.cart.service.CartService;
import com.example.spring.test.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public void createCart(@RequestBody CreateCartRequest createCartRequest) {
        cartService.createCart(createCartRequest);
    }

    @GetMapping("/{memberId}")
    public List<ProductResponse> getCart(@PathVariable Long memberId) {
        // return cartservice에 getCart() 호출
        List<ProductResponse> carts = cartService.getCart(memberId);

        return carts;
    }
}
