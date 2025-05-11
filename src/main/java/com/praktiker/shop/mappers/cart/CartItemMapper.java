package com.praktiker.shop.mappers.cart;

import com.praktiker.shop.dto.cart.CartItemRequest;
import com.praktiker.shop.dto.cart.CartItemResponse;
import com.praktiker.shop.entities.cart.Cart;
import com.praktiker.shop.entities.cart.CartItem;
import com.praktiker.shop.entities.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "id", ignore = true)
    CartItem toEntity(CartItemRequest request, Cart cart, Product product);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "pricePerUnit", source = "product.price")
    CartItemResponse toResponse(CartItem cartItem);
}
