package com.praktiker.shop.mappers.cart;

import com.praktiker.shop.dto.cart.CartResponse;
import com.praktiker.shop.entities.cart.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartResponse toResponse(Cart cart);
}
