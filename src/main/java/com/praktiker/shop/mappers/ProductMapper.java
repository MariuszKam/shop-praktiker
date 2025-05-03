package com.praktiker.shop.mappers;

import com.praktiker.shop.dto.order.OrderItemRequest;

import java.util.List;

public class ProductMapper {

    public static Long toProductId(OrderItemRequest request) {
        return request.getProductId();
    }

    public static List<Long> toProductsId(List<OrderItemRequest> requests) {
        return requests.stream().map(OrderItemRequest::getProductId).toList();
    }
}
