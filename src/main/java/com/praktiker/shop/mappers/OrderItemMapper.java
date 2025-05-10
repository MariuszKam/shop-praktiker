package com.praktiker.shop.mappers;

import com.praktiker.shop.dto.order.OrderItemRequest;
import com.praktiker.shop.dto.order.OrderItemResponse;
import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    OrderItem toEntity(OrderItemRequest request, Product product, Order order);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "pricePerUnit", source = "product.price")
    OrderItemResponse toResponse(OrderItem orderItem);

    List<OrderItemResponse> toResponse(List<OrderItem> orderItems);
}